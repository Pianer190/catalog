package ru.catalog.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumerInitializer;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ExceptionUtils;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.ReflectionUtils;
import ru.catalog.annotations.Users;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class ParameterizedTestExtension implements TestTemplateInvocationContextProvider {
    private static final String METHOD_CONTEXT_KEY = "context";
    static final String ARGUMENT_MAX_LENGTH_KEY = "junit.jupiter.params.displayname.argument.maxlength";
    private static final String DEFAULT_DISPLAY_NAME = "{default_display_name}";
    static final String DISPLAY_NAME_PATTERN_KEY = "junit.jupiter.params.displayname.default";

    ParameterizedTestExtension() {
    }

    public boolean supportsTestTemplate(ExtensionContext context) {
        if (context.getTestMethod().isEmpty()) {
            return false;
        } else {
            Method testMethod = context.getTestMethod().get();
            if (!AnnotationUtils.isAnnotated(testMethod, Users.class)) {
                return false;
            } else {
                ParameterizedTestMethodContext methodContext = new ParameterizedTestMethodContext(testMethod);
                Preconditions.condition(methodContext.hasPotentiallyValidSignature(), () -> String.format("@ParameterizedTest method [%s] declares formal parameters in an invalid order: argument aggregators must be declared after any indexed arguments and before any arguments resolved by another ParameterResolver.", testMethod.toGenericString()));
                this.getStore(context).put("context", methodContext);
                return true;
            }
        }
    }

    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        Method templateMethod = extensionContext.getRequiredTestMethod();
        String displayName = extensionContext.getDisplayName();
        ParameterizedTestMethodContext methodContext = this.getStore(extensionContext).get("context", ParameterizedTestMethodContext.class);
        int argumentMaxLength = extensionContext.getConfigurationParameter("junit.jupiter.params.displayname.argument.maxlength", Integer::parseInt).orElse(512);
        ParameterizedTestNameFormatter formatter = this.createNameFormatter(extensionContext, templateMethod, methodContext, displayName, argumentMaxLength);
        AtomicLong invocationCount = new AtomicLong(0L);
        return AnnotationUtils.findRepeatableAnnotations(templateMethod, ArgumentsSource.class).stream().map(ArgumentsSource::value).map(this::instantiateArgumentsProvider).map((provider) -> AnnotationConsumerInitializer.initialize(templateMethod, provider)).flatMap((provider) -> arguments(provider, extensionContext)).map(Arguments::get).map((arguments) -> {
            invocationCount.incrementAndGet();
            return this.createInvocationContext(formatter, methodContext, arguments);
        }).onClose(() -> Preconditions.condition(invocationCount.get() > 0L, "Configuration error: You must configure at least one set of arguments for this @ParameterizedTest"));
    }

    private ArgumentsProvider instantiateArgumentsProvider(Class<? extends ArgumentsProvider> clazz) {
        return ReflectionUtils.newInstance(clazz);
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(ParameterizedTestExtension.class, context.getRequiredTestMethod()));
    }

    private TestTemplateInvocationContext createInvocationContext(ParameterizedTestNameFormatter formatter, ParameterizedTestMethodContext methodContext, Object[] arguments) {
        return new ParameterizedTestInvocationContext(formatter, methodContext, arguments);
    }

    private ParameterizedTestNameFormatter createNameFormatter(ExtensionContext extensionContext, Method templateMethod, ParameterizedTestMethodContext methodContext, String displayName, int argumentMaxLength) {
        Users parameterizedTest = AnnotationUtils.findAnnotation(templateMethod, Users.class).get();
        String pattern = parameterizedTest.name().equals("{default_display_name}") ? extensionContext.getConfigurationParameter("junit.jupiter.params.displayname.default").orElse("[{index}] {argumentsWithNames}") : parameterizedTest.name();
        pattern = Preconditions.notBlank(pattern.trim(), () -> String.format("Configuration error: @ParameterizedTest on method [%s] must be declared with a non-empty name.", templateMethod));
        return new ParameterizedTestNameFormatter(pattern, displayName, methodContext, argumentMaxLength);
    }

    protected static Stream<? extends Arguments> arguments(ArgumentsProvider provider, ExtensionContext context) {
        try {
            return provider.provideArguments(context);
        } catch (Exception var3) {
            throw ExceptionUtils.throwAsUncheckedException(var3);
        }
    }
}
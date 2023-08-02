package ru.catalog.extensions.parameterized;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;


class ParameterizedTestParameterResolver implements ParameterResolver, AfterTestExecutionCallback {
    private static final ExtensionContext.Namespace NAMESPACE = Namespace.create(ParameterizedTestParameterResolver.class);
    private final ParameterizedTestMethodContext methodContext;
    private final Object[] arguments;

    ParameterizedTestParameterResolver(ParameterizedTestMethodContext methodContext, Object[] arguments) {
        this.methodContext = methodContext;
        this.arguments = arguments;
    }

    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Executable declaringExecutable = parameterContext.getDeclaringExecutable();
        Method testMethod = extensionContext.getTestMethod().orElse(null);
        int parameterIndex = parameterContext.getIndex();
        if (!declaringExecutable.equals(testMethod)) {
            return false;
        } else if (this.methodContext.isAggregator(parameterIndex)) {
            return true;
        } else if (this.methodContext.hasAggregator()) {
            return parameterIndex < this.methodContext.indexOfFirstAggregator();
        } else {
            return parameterIndex < this.arguments.length;
        }
    }

    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return this.methodContext.resolve(parameterContext, this.extractPayloads(this.arguments));
    }

    public void afterTestExecution(ExtensionContext context) {
    }

    private Object[] extractPayloads(Object[] arguments) {
        return Arrays.stream(arguments).map((argument) -> argument instanceof Named ? ((Named)argument).getPayload() : argument).toArray();
    }

    private record CloseableArgument(AutoCloseable autoCloseable) implements ExtensionContext.Store.CloseableResource {

        public void close() throws Throwable {
                this.autoCloseable.close();
            }
        }
}

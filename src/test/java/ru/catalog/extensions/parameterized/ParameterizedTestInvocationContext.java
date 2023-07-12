package ru.catalog.extensions.parameterized;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

class ParameterizedTestInvocationContext implements TestTemplateInvocationContext {
    private final ParameterizedTestNameFormatter formatter;
    private final ParameterizedTestMethodContext methodContext;
    private final Object[] arguments;

    ParameterizedTestInvocationContext(ParameterizedTestNameFormatter formatter, ParameterizedTestMethodContext methodContext, Object[] arguments) {
        this.formatter = formatter;
        this.methodContext = methodContext;
        this.arguments = arguments;
    }

    public String getDisplayName(int invocationIndex) {
        return this.formatter.format(invocationIndex, this.arguments);
    }

    public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(new ParameterizedTestParameterResolver(this.methodContext, this.arguments));
    }
}

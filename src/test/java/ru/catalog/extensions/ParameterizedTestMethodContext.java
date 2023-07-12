package ru.catalog.extensions;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.aggregator.DefaultArgumentsAccessor;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.DefaultArgumentConverter;
import org.junit.jupiter.params.support.AnnotationConsumerInitializer;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.platform.commons.util.StringUtils;

class ParameterizedTestMethodContext {
    private final Parameter[] parameters;
    private final ParameterizedTestMethodContext.Resolver[] resolvers;
    private final List<ParameterizedTestMethodContext.ResolverType> resolverTypes;

    ParameterizedTestMethodContext(Method testMethod) {
        this.parameters = testMethod.getParameters();
        this.resolvers = new ParameterizedTestMethodContext.Resolver[this.parameters.length];
        this.resolverTypes = new ArrayList(this.parameters.length);
        Parameter[] var2 = this.parameters;
        int var3 = var2.length;

        for (Parameter parameter : var2) {
            this.resolverTypes.add(isAggregator(parameter) ? ResolverType.AGGREGATOR : ResolverType.CONVERTER);
        }

    }

    private static boolean isAggregator(Parameter parameter) {
        return ArgumentsAccessor.class.isAssignableFrom(parameter.getType()) || AnnotationUtils.isAnnotated(parameter, AggregateWith.class);
    }

    boolean hasPotentiallyValidSignature() {
        int indexOfPreviousAggregator = -1;

        for(int i = 0; i < this.getParameterCount(); ++i) {
            if (this.isAggregator(i)) {
                if (indexOfPreviousAggregator != -1 && i != indexOfPreviousAggregator + 1) {
                    return false;
                }

                indexOfPreviousAggregator = i;
            }
        }

        return true;
    }

    int getParameterCount() {
        return this.parameters.length;
    }

    Optional<String> getParameterName(int parameterIndex) {
        if (parameterIndex >= this.getParameterCount()) {
            return Optional.empty();
        } else {
            Parameter parameter = this.parameters[parameterIndex];
            if (!parameter.isNamePresent()) {
                return Optional.empty();
            } else {
                return this.hasAggregator() && parameterIndex >= this.indexOfFirstAggregator() ? Optional.empty() : Optional.of(parameter.getName());
            }
        }
    }

    boolean hasAggregator() {
        return this.resolverTypes.contains(ParameterizedTestMethodContext.ResolverType.AGGREGATOR);
    }

    boolean isAggregator(int parameterIndex) {
        return this.resolverTypes.get(parameterIndex) == ParameterizedTestMethodContext.ResolverType.AGGREGATOR;
    }

    int indexOfFirstAggregator() {
        return this.resolverTypes.indexOf(ParameterizedTestMethodContext.ResolverType.AGGREGATOR);
    }

    Object resolve(ParameterContext parameterContext, Object[] arguments) {
        return this.getResolver(parameterContext).resolve(parameterContext, arguments);
    }

    private ParameterizedTestMethodContext.Resolver getResolver(ParameterContext parameterContext) {
        int index = parameterContext.getIndex();
        if (this.resolvers[index] == null) {
            this.resolvers[index] = this.resolverTypes.get(index).createResolver(parameterContext);
        }

        return this.resolvers[index];
    }

    private static ParameterResolutionException parameterResolutionException(String message, Exception cause, ParameterContext parameterContext) {
        String fullMessage = message + " at index " + parameterContext.getIndex();
        if (StringUtils.isNotBlank(cause.getMessage())) {
            fullMessage = fullMessage + ": " + cause.getMessage();
        }

        return new ParameterResolutionException(fullMessage, cause);
    }

    interface Resolver {
        Object resolve(ParameterContext var1, Object[] var2);
    }

    enum ResolverType {
        CONVERTER {
            ParameterizedTestMethodContext.Resolver createResolver(ParameterContext parameterContext) {
                try {
                    return AnnotationUtils.findAnnotation(parameterContext.getParameter(), ConvertWith.class).map(ConvertWith::value).map((clazz) -> (ArgumentConverter)ReflectionUtils.newInstance(clazz, new Object[0])).map((converter) -> AnnotationConsumerInitializer.initialize(parameterContext.getParameter(), converter)).map(Converter::new).orElse(Converter.DEFAULT);
                } catch (Exception var3) {
                    throw ParameterizedTestMethodContext.parameterResolutionException("Error creating ArgumentConverter", var3, parameterContext);
                }
            }
        },
        AGGREGATOR {
            ParameterizedTestMethodContext.Resolver createResolver(ParameterContext parameterContext) {
                try {
                    return AnnotationUtils.findAnnotation(parameterContext.getParameter(), AggregateWith.class).map(AggregateWith::value).map((clazz) -> (ArgumentsAggregator)ReflectionSupport.newInstance(clazz, new Object[0])).map(Aggregator::new).orElse(Aggregator.DEFAULT);
                } catch (Exception var3) {
                    throw ParameterizedTestMethodContext.parameterResolutionException("Error creating ArgumentsAggregator", var3, parameterContext);
                }
            }
        };

        ResolverType() {
        }

        abstract ParameterizedTestMethodContext.Resolver createResolver(ParameterContext var1);
    }

    static class Aggregator implements ParameterizedTestMethodContext.Resolver {
        private static final ParameterizedTestMethodContext.Aggregator DEFAULT = new ParameterizedTestMethodContext.Aggregator((accessor, context) -> accessor);
        private final ArgumentsAggregator argumentsAggregator;

        Aggregator(ArgumentsAggregator argumentsAggregator) {
            this.argumentsAggregator = argumentsAggregator;
        }

        public Object resolve(ParameterContext parameterContext, Object[] arguments) {
            ArgumentsAccessor accessor = new DefaultArgumentsAccessor(arguments);

            try {
                return this.argumentsAggregator.aggregateArguments(accessor, parameterContext);
            } catch (Exception var5) {
                throw ParameterizedTestMethodContext.parameterResolutionException("Error aggregating arguments for parameter", var5, parameterContext);
            }
        }
    }

    static class Converter implements ParameterizedTestMethodContext.Resolver {
        private static final ParameterizedTestMethodContext.Converter DEFAULT;
        private final ArgumentConverter argumentConverter;

        Converter(ArgumentConverter argumentConverter) {
            this.argumentConverter = argumentConverter;
        }

        public Object resolve(ParameterContext parameterContext, Object[] arguments) {
            Object argument = arguments[parameterContext.getIndex()];

            try {
                return this.argumentConverter.convert(argument, parameterContext);
            } catch (Exception var5) {
                throw ParameterizedTestMethodContext.parameterResolutionException("Error converting parameter", var5, parameterContext);
            }
        }

        static {
            DEFAULT = new ParameterizedTestMethodContext.Converter(DefaultArgumentConverter.INSTANCE);
        }
    }
}

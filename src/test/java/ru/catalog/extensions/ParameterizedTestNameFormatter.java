package ru.catalog.extensions;

import java.text.Format;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Named;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.StringUtils;

class ParameterizedTestNameFormatter {
    private static final char ELLIPSIS = '…';
    private final String pattern;
    private final String displayName;
    private final ParameterizedTestMethodContext methodContext;
    private final int argumentMaxLength;

    ParameterizedTestNameFormatter(String pattern, String displayName, ParameterizedTestMethodContext methodContext, int argumentMaxLength) {
        this.pattern = pattern;
        this.displayName = displayName;
        this.methodContext = methodContext;
        this.argumentMaxLength = argumentMaxLength;
    }

    String format(int invocationIndex, Object... arguments) {
        try {
            return this.formatSafely(invocationIndex, arguments);
        } catch (Exception var5) {
            String message = "The display name pattern defined for the parameterized test is invalid. See nested exception for further details.";
            throw new JUnitException(message, var5);
        }
    }

    private String formatSafely(int invocationIndex, Object[] arguments) {
        Object[] namedArguments = this.extractNamedArguments(arguments);
        String pattern = this.prepareMessageFormatPattern(invocationIndex, namedArguments);
        MessageFormat format = new MessageFormat(pattern);
        Object[] humanReadableArguments = this.makeReadable(format, namedArguments);
        return format.format(humanReadableArguments);
    }

    private Object[] extractNamedArguments(Object[] arguments) {
        return Arrays.stream(arguments).map((argument) -> argument instanceof Named ? ((Named)argument).getName() : argument).toArray();
    }

    private String prepareMessageFormatPattern(int invocationIndex, Object[] arguments) {
        String result = this.pattern.replace("{displayName}", this.displayName).replace("{index}", String.valueOf(invocationIndex));
        if (result.contains("{argumentsWithNames}")) {
            result = result.replace("{argumentsWithNames}", this.argumentsWithNamesPattern(arguments));
        }

        if (result.contains("{arguments}")) {
            result = result.replace("{arguments}", this.argumentsPattern(arguments));
        }
        return result;
    }

    private String argumentsWithNamesPattern(Object[] arguments) {
        return IntStream.range(0, arguments.length).mapToObj((index) -> this.methodContext.getParameterName(index).map((name) -> name + "=").orElse("") + "{" + index + "}").collect(Collectors.joining(", "));
    }

    private String argumentsPattern(Object[] arguments) {
        return IntStream.range(0, arguments.length).mapToObj((index) -> "{" + index + "}").collect(Collectors.joining(", "));
    }

    private Object[] makeReadable(MessageFormat format, Object[] arguments) {
        Format[] formats = format.getFormatsByArgumentIndex();
        Object[] result = Arrays.copyOf(arguments, Math.min(arguments.length, formats.length), Object[].class);

        for(int i = 0; i < result.length; ++i) {
            if (formats[i] == null) {
                result[i] = this.truncateIfExceedsMaxLength(StringUtils.nullSafeToString(arguments[i]));
            }
        }

        return result;
    }

    private String truncateIfExceedsMaxLength(String argument) {
        return argument != null && argument.length() > this.argumentMaxLength ? argument.substring(0, this.argumentMaxLength - 1) + '…' : argument;
    }
}
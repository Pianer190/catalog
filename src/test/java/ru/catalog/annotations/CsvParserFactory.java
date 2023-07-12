package ru.catalog.annotations;

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.junit.platform.commons.util.Preconditions;

import java.lang.annotation.Annotation;

class CsvParserFactory {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final char EMPTY_CHAR = '\u0000';
    private static final boolean COMMENT_PROCESSING_FOR_CSV_FILE_SOURCE = true;

    CsvParserFactory() {
    }

    static CsvParser createParserFor(CsvSource annotation) {
        String delimiter = selectDelimiter(annotation, annotation.delimiter(), annotation.delimiterString());
        boolean commentProcessingEnabled = !annotation.textBlock().isEmpty();
        return createParser(delimiter, annotation.quoteCharacter(), annotation.emptyValue(), annotation.maxCharsPerColumn(), commentProcessingEnabled, annotation.ignoreLeadingAndTrailingWhitespace());
    }

    private static String selectDelimiter(Annotation annotation, char delimiter, String delimiterString) {
        Preconditions.condition(delimiter == 0 || delimiterString.isEmpty(), () -> "The delimiter and delimiterString attributes cannot be set simultaneously in " + annotation);
        if (delimiter != 0) {
            return String.valueOf(delimiter);
        } else {
            return !delimiterString.isEmpty() ? delimiterString : ",";
        }
    }

    private static CsvParser createParser(String delimiter, char quote, String emptyValue, int maxCharsPerColumn, boolean commentProcessingEnabled, boolean ignoreLeadingAndTrailingWhitespace) {
        return new CsvParser(createParserSettings(delimiter, quote, emptyValue, maxCharsPerColumn, commentProcessingEnabled, ignoreLeadingAndTrailingWhitespace));
    }

    private static CsvParserSettings createParserSettings(String delimiter, char quote, String emptyValue, int maxCharsPerColumn, boolean commentProcessingEnabled, boolean ignoreLeadingAndTrailingWhitespace) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(false);
        settings.getFormat().setDelimiter(delimiter);
        settings.getFormat().setLineSeparator("\n");
        settings.getFormat().setQuote(quote);
        settings.getFormat().setQuoteEscape(quote);
        settings.setEmptyValue(emptyValue);
        settings.setCommentProcessingEnabled(commentProcessingEnabled);
        settings.setAutoConfigurationEnabled(false);
        settings.setIgnoreLeadingWhitespaces(ignoreLeadingAndTrailingWhitespace);
        settings.setIgnoreTrailingWhitespaces(ignoreLeadingAndTrailingWhitespace);
        Preconditions.condition(maxCharsPerColumn > 0, () -> "maxCharsPerColumn must be a positive number: " + maxCharsPerColumn);
        settings.setMaxCharsPerColumn(maxCharsPerColumn);
        return settings;
    }
}

package util.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


public class SeparatedValuesParserTest {
    @Nested
    @DisplayName("Test parseLineToArray method")
    class ParseLineToArrayTests {

        @ParameterizedTest
        @CsvSource({"_,just_check_ordinary_statement", "-,just-check-ordinary-statement"
                , "\\|,just|check|ordinary|statement"
                , "-\\|\\|tr\\*,just-||tr*check-||tr*ordinary-||tr*statement"})
        @DisplayName("Ordinary line with some separator should be parsed to array")
        void shouldParseOrdinaryLineToArray(String separator_regex, String line) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{"just", "check", "ordinary", "statement"};

            String[] actual = parser.parseLineToArray(line);

            Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Empty line should be parsed to empty array")
        void parseLineToArray_ShouldParseEmptyLineToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{};

            String[] actual = parser.parseLineToArray("");

            Assertions.assertArrayEquals(expected, actual, "Expected parsed empty line into empty array");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Null should be parsed to empty array")
        void parseLineToArray_ShouldParseNullToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{};

            String[] actual = parser.parseLineToArray(null);

            Assertions.assertArrayEquals(expected, actual, "Expected parsed null into empty array");
        }
    }

    @Nested
    @DisplayName("Test parseArrayToLine method")
    class ParseArrayToLineTests {

        @ParameterizedTest
        @CsvSource({"_,just_check_ordinary_statement", "-,just-check-ordinary-statement"
                , "\\|,just|check|ordinary|statement"
                , "-\\|\\|tr\\*,just-||tr*check-||tr*ordinary-||tr*statement"})
        @DisplayName("Array should be parsed to line with some separator")
        void shouldParseArrayToLine(String separator_regex, String expectedLine) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String expected = expectedLine;

            String actual = parser.parseArrayToLine(new String[]{"just", "check", "ordinary", "statement"});

            Assertions.assertEquals(expected, actual, "Expected parsed array into line");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Empty array should be parsed to empty line")
        void parseArrayToLine_ShouldParseEmptyArrayToEmptyLine(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String expected = "";

            String actual = parser.parseArrayToLine(new String[]{});

            Assertions.assertEquals(expected, actual, "Expected parsed empty array into empty line");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Null should be parsed to empty line")
        void parseArrayToLine_ShouldParseNullToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String expected = "";

            String actual = parser.parseArrayToLine(null);

            Assertions.assertEquals(expected, actual, "Expected parsed null into empty line");
        }
    }

    @Nested
    @DisplayName("Test parseLineToStudent method")
    class ParseLineToStudentTests{

    }


}

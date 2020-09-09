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
    @DisplayName("Test parseToLine method")
    class ParseLineToArray {

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


        // TODO check, tried new names
        @Test
        void parseLineToArray_WithSpaceSeparatorParser_ShouldParseLineToArray() {
            SeparatedValuesParser parser = new SeparatedValuesParser(" ");
            String[] expected = new String[]{"just", "check", "ordinary", "statement"};

            String[] actual = parser.parseLineToArray("just check ordinary statement ");

            Assertions.assertArrayEquals(expected, actual, "Expected parsed line to array");
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

        @Test
        @DisplayName("Null should be parsed to empty array")
        void parseLineToArray_WithSpaceSeparatorParser_ShouldParseNullToEmptyArray() {
            SeparatedValuesParser parser = new SeparatedValuesParser(" ");
            String[] expected = new String[]{};

            String[] actual = parser.parseLineToArray(null);

            Assertions.assertArrayEquals(expected, actual, "Expected parsed null into empty array");
        }

        //TODO make negative tests for lineToArray
    }
    /** test parseArrayToLine(separator)*/


    @ParameterizedTest
    @CsvSource({"_,just_check_ordinary_statement", "-,just-check-ordinary-statement"
            , "\\|,just|check|ordinary|statement"
            , "-\\|\\|tr\\*,just-||tr*check-||tr*ordinary-||tr*statement"})
    @DisplayName("Array should be parsed to line with some separator")
    void shouldParseArrayToLineWithUnderscoreSeparator(String separator_regex, String expectedLine){
        SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
        String expected = expectedLine;

        String actual = parser.parseArrayToLine(new String[]{"just","check", "ordinary", "statement"});

        Assertions.assertEquals(expected, actual, "Expected parsed array into line");
    }

    @Test
    @DisplayName("Empty array should be parsed to empty line")
    void parseArrayToLine_WithSpaceSeparatorParser_ShouldParseEmptyArrayToEmptyLine(){
        SeparatedValuesParser parser = new SeparatedValuesParser(" ");
        String expected = "";

        String actual = parser.parseArrayToLine(new String[]{});

        Assertions.assertEquals(expected, actual, "Expected parsed empty array into empty line");
    }

    @Test
    @DisplayName("Null should be parsed to empty line")
    void parseArrayToLine_WithSpaceSeparatorParser_ShouldParseNullToEmptyArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser(" ");
        String expected = "";

        String actual = parser.parseArrayToLine(null);

        Assertions.assertEquals(expected, actual, "Expected parsed null into empty line");
    }

    //TODO make negative tests for arrayToLine


}

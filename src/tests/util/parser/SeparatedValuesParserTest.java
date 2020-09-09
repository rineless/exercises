package util.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class SeparatedValuesParserTest {

    /**test parseLineToArray(separator)*/
    @Test
    @DisplayName("Ordinary line with _ separator should be parsed to array")
    void shouldParseOrdinaryLineWithUnderscoreSeparatorToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser("_");
        String[] expected = new String[]{"just","check", "ordinary", "statement"};

        String[] actual = parser.parseLineToArray("just_check_ordinary_statement");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
    }

    @Test
    @DisplayName("Ordinary line with | separator should be parsed to array")
    void shouldParseOrdinaryLineWithVerticalLineSeparatorToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser("\\|");
        String[] expected = new String[]{"just","check", "ordinary", "statement"};

        String[] actual = parser.parseLineToArray("just|check|ordinary|statement");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
    }

    @Test
    @DisplayName("Ordinary line with - separator should be parsed to array")
    void shouldParseOrdinaryLineWithHorizontalLineSeparatorToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser("-");
        String[] expected = new String[]{"just","check", "ordinary", "statement"};

        String[] actual = parser.parseLineToArray("just-check-ordinary-statement");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
    }

    @Test
    @DisplayName("Ordinary line with -||tr* separator should be parsed to array")
    void shouldParseOrdinaryLineWithBunchOfSymbolsSeparatorToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser("-\\|\\|tr\\*");
        String[] expected = new String[]{"just","check", "ordinary", "statement"};

        String[] actual = parser.parseLineToArray("just-||tr*check-||tr*ordinary-||tr*statement");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
    }


    // TODO check, tried new names
    @Test
    void parseLineToArray_WithSpaceSeparatorParser_ShouldParseLineToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser(" ");
        String[] expected = new String[]{"just","check", "ordinary", "statement"};

        String[] actual = parser.parseLineToArray("just check ordinary statement ");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed line to array");
    }

    @ParameterizedTest
    @ValueSource(strings = {","})
    @DisplayName("Empty line should be parsed to array (with comma separator parser)")
    void parseLineToArray_WithCommaSeparatorParser_ShouldParseEmptyLineToEmptyArray(String separator_regex){
        SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
        String[] expected = new String[]{};

        String[] actual = parser.parseLineToArray("");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed empty line into empty array");
    }

    @Test
    @DisplayName("Empty line should be parsed to array (with space separator parser)")
    void parseLineToArray_WithSpaceSeparatorParser_ShouldParseEmptyLineToArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser(" ");
        String[] expected = new String[]{};

        String[] actual = parser.parseLineToArray("");

        Assertions.assertArrayEquals(expected, actual, "Expected parsed empty line into empty array");
    }

    @Test
    @DisplayName("Null should be parsed to empty array")
    void parseLineToArray_WithSpaceSeparatorParser_ShouldParseNullToEmptyArray(){
        SeparatedValuesParser parser = new SeparatedValuesParser(" ");
        String[] expected = new String[]{};

        String[] actual = parser.parseLineToArray(null);

        Assertions.assertArrayEquals(expected, actual, "Expected parsed null into empty array");
    }

    //TODO make negative tests for lineToArray

    /** test parseArrayToLine(separator)*/

    @Test
    @DisplayName("Array should be parsed to line with _ separator")
    void shouldParseArrayToLineWithUnderscoreSeparator(){
        SeparatedValuesParser parser = new SeparatedValuesParser("_");
        String expected = "just_check_ordinary_statement";

        String actual = parser.parseArrayToLine(new String[]{"just","check", "ordinary", "statement"});

        Assertions.assertEquals(expected, actual, "Expected parsed array into line");
    }

    @Test
    @DisplayName("Array should be parsed to line with | separator")
    void shouldParseArrayToLineWithVerticalLineSeparator(){
        SeparatedValuesParser parser = new SeparatedValuesParser("\\|");
        String expected = "just|check|ordinary|statement";

        String actual = parser.parseArrayToLine(new String[]{"just","check", "ordinary", "statement"});

        Assertions.assertEquals(expected, actual, "Expected parsed array into line");
    }

    @Test
    @DisplayName("Array should be parsed to line with - separator")
    void shouldParseArrayToLineWithHorizontalLineSeparator(){
        SeparatedValuesParser parser = new SeparatedValuesParser("-");
        String expected = "just-check-ordinary-statement";

        String actual = parser.parseArrayToLine(new String[]{"just","check", "ordinary", "statement"});

        Assertions.assertEquals(expected, actual, "Expected parsed array into line");
    }

    @Test
    @DisplayName("Array should be parsed to line with -||tr* separator")
    void shouldParseArrayToLineWithBunchOfSymbolsSeparator(){
        SeparatedValuesParser parser = new SeparatedValuesParser("-\\|\\|tr\\*");
        String expected = "just-||tr*check-||tr*ordinary-||tr*statement";

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

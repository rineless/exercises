package util.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class SeparatedValuesParserTest {

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


}

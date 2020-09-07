package util.parser;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SeparatedValuesParser implements ILineParser {
    private final String separator;

    public SeparatedValuesParser(String separator_regex) {
        this.separator = separator_regex;
    }

    public String[] parseLineToArray(String line) {
        if (line != null)
            return line.split(separator);
        return null;
    }

    public String parseArrayToLine(String[] array){
        String line = Arrays.stream(array).map(word -> word+separator).collect(Collectors.joining());
        return line.substring(0, line.length() - separator.length());
    }
}

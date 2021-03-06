package util.parser;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SeparatedValuesParser implements ILineParser {
    private final String separator_regex;

    public SeparatedValuesParser(String separator_regex) {
        this.separator_regex = separator_regex;
    }

    public String[] parseLineToArray(String line) {
        if (line != null && !line.contentEquals("")) {
            return line.split(separator_regex);
        }
        return new String[]{};
    }

    public String parseArrayToLine(String[] array){
        if(array != null) {
            if(array.length!=0) {
                String separator = separator_regex.replace("\\", "");
                String line = Arrays.stream(array).map(word -> word == null ? "" : word).map(word -> word + separator).collect(Collectors.joining());
                return line.substring(0, line.length() - separator.length());
            }
        }

        return "";
    }
}

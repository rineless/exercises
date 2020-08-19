package util;

public class DSVParser implements LineParser {
    //Dot Separated Value
    public String[] parseLineToArray(String line) {
        if (line != null)
            return line.split(".");
        return null;
    }

}

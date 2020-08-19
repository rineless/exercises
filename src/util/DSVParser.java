package util;

public class DSVParser implements ILineParser {
    //Dot Separated Value
    public String[] parseLineToArray(String line) {
        if (line != null)
            return line.split(".");
        return null;
    }

}

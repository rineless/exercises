package util;

public class CSVParser implements ILineParser {

    public String[] parseLineToArray(String line){
    if(line!=null)
        return line.split(",");
    return null;
    }
}

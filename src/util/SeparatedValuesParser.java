package util;

public class SeparatedValuesParser implements ILineParser{
    private final String separator;

    public SeparatedValuesParser(String separator){
        this.separator = separator;
    }

    public String[] parseLineToArray(String line){
        if(line!=null)
            return line.split(separator);
        return null;
    }
}

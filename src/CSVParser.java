public class CSVParser implements LineParser{

    public String[] parseLineToArray(String line){
    if(line!=null)
        return line.split(",");
    return null;
    }
}

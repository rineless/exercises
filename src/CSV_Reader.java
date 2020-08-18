import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class CSV_Reader {
    private final String path;

    //TODO if String is empty, incorrect or null
    public CSV_Reader(String path){
        this.path = path;
    }

    //TODO
    public LinkedList<String[]> getDataAsList(){
        return null;
    }

    //TODO
    private Stream<String> getLines(){
        try {
            return Files.lines(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO if null
    private String[] getDataFromLine(String line){
        return null;
    }

    public String getPath(){
        return path;
    }


}

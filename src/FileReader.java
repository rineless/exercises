import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class FileReader {
    private final String PATH;

    public FileReader(String path){
        this.PATH = path;
    }

    public ArrayList<String[]> getDataAsList(){
        if(getLines()!=null){
            ArrayList<String[]> data = new ArrayList<>();
            getLines().forEach(line -> data.add(getDataFromLine(line)));
            return data;
        }
        return null;
    }

    private Stream<String> getLines(){
        try {
            return Files.lines(Path.of(PATH));
        } catch (IOException e) {
            System.out.println("Path given incorrect");
            return null;
        }
    }

    private String[] getDataFromLine(String line){
        return line.split(",");
    }

    public String getPath(){
        return PATH;
    }

}

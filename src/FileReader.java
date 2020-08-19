import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader extends Reader{
    private final String path;

    public FileReader(String path){
        this.path = path;
    }

    public List<String> receiveLinesAsList(){
        if(receiveLinesFromFileAsStream()!=null){
            return receiveLinesFromFileAsStream().collect(Collectors.toList());
        }
        return null;
    }

    private Stream<String> receiveLinesFromFileAsStream(){
        try {
            return Files.lines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Path given incorrect");
            return null;
        }
    }

    public String getPath(){
        return path;
    }

}

package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader extends Reader {
    private final String path;

    public FileReader(String path) {
        this.path = path;
    }

    //TODO check exception, maybe make a bit different
    public List<String> receiveLinesAsList() {
        try {
            return Files.lines(Path.of(path)).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Path given incorrect. Created empty list");
            return new ArrayList<String>();
        }
    }

    public String getPath() {
        return path;
    }

}

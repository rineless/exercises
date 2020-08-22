package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader extends Reader {

    //TODO check exception, maybe make a bit different
    public List<String> receiveLinesAsList(String path) {
        try {
            if (Files.exists(Path.of(path)) && Files.isReadable(Path.of(path)))
                return Files.lines(Path.of(path)).collect(Collectors.toList());
            else {
                throw new IllegalArgumentException("Readable file not found.");
            }
        } catch (IOException | IllegalArgumentException exp) {
            System.out.println("Path given incorrect. Created empty list");
            return new ArrayList<String>();
        }
    }

}

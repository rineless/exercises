package util.reader;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader extends Reader {


    public List<String> receiveLinesAsList(Path path) {
        try {
            if (Objects.nonNull(path)) {
                if (Files.exists(path) && Files.isRegularFile(path)
                        && Files.isReadable(path))
                    return Files.lines(path).collect(Collectors.toList());
                else {
                    throw new IllegalArgumentException("Readable file not found.");
                }
            }
            return new ArrayList<>();
        } catch (IOException | IllegalArgumentException exp) {
            if(exp instanceof IllegalArgumentException)
                System.out.println(exp.getMessage());
            System.out.println("Path given incorrect. Created empty list");
            return new ArrayList<>();
        }
    }

    public List<String> receiveLinesAsList(String path) {
        if (Objects.nonNull(path)){
            System.out.println("Path cannot be null. Created empty list");
            return new ArrayList<>();
        }

        return receiveLinesAsList(Path.of(path));
    }



}
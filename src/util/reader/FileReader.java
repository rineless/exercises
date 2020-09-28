package util.reader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader extends Reader {


    public List<String> receiveLinesAsList(String pathInResources) {
        try {
            Path path = Path.of(findAbsolutePathFromRelativeToResourceFolder(pathInResources));
            if (Files.exists(path) && Files.isRegularFile(path)
                    && Files.isReadable(path))
                return Files.lines(path).collect(Collectors.toList());
            else {
                throw new IllegalArgumentException("Readable file not found.");
            }
        } catch (IOException | IllegalArgumentException exp) {
            if(exp instanceof IllegalArgumentException)
                System.out.println(exp.getMessage());
            System.out.println("Path given incorrect. Created empty list");
            return new ArrayList<>();
        }
    }

    private String findAbsolutePathFromRelativeToResourceFolder(String pathInResources)
            throws IllegalArgumentException {
        URL url = getClass().getClassLoader().getResource(pathInResources);
        if (url == null)
            throw new IllegalArgumentException("Cannot find " + pathInResources + " file");
        return new File(url.getPath()).toString();
    }

}

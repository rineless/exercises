package util.finder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.nio.file.FileSystems.getFileSystem;

public class PathFinder {
    private static FileSystem fileSystemForResourcesFolder;

    public static void findByName(String name, File rootDirectory, List<String> paths)
            throws IllegalArgumentException {
        if (name == null | rootDirectory == null | paths == null) {
            throw new IllegalArgumentException("Null cannot be resolved into name or rootDirectory or paths");
        }
        if (!rootDirectory.isDirectory()) {
            throw new IllegalArgumentException(" Given rootDirectory is not a directory");
        }

        for (File file : rootDirectory.listFiles()) {
            if (file.isDirectory())
                findByName(name, file, paths);

            if (file.getName().contentEquals(name))
                paths.add(file.getAbsolutePath());
        }

    }

    public static Path findFromResources(String path) {
        try {
            URL url = new PathFinder().getClass().getClassLoader().getResource(path);
            if (Objects.nonNull(url)) {
                if (url.toString().contains("!")) {
                    String[] array = url.toURI().toString().split("!");
                    if (Objects.isNull(fileSystemForResourcesFolder)) {
                        fileSystemForResourcesFolder = FileSystems.newFileSystem(URI.create(array[0]), new HashMap<>());
                    } else
                        fileSystemForResourcesFolder = FileSystems.getFileSystem(URI.create(array[0]));
                    return fileSystemForResourcesFolder.getPath(array[1]);
                } else
                    return Path.of(url.toURI());
            } else {
                System.out.println("File not found. Return empty path");
                return Path.of("");
            }
        } catch (URISyntaxException | IOException exp) {
            System.out.println(exp.getMessage());
            System.out.println("Return empty path");
            return Path.of("");
        }
    }
}

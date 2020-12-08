package util.finder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.StudentsService;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class PathFinder {
    private final static Logger logger = LogManager.getLogger(PathFinder.class);

    private static FileSystem fileSystemForResourcesFolder;
    private static final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg"
            , Locale.getDefault());

    public static void findByName(String name, File rootDirectory, List<String> paths)
            throws IllegalArgumentException {
        if (name == null | rootDirectory == null | paths == null) {
            throw new IllegalArgumentException(properties.getString("pathFinder.null_path_dir_root"));
        }
        if (!rootDirectory.isDirectory()) {
            throw new IllegalArgumentException(properties.getString("pathFinder.not_dir"));
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
                logger.warn(properties.getString("pathFinder.file_not_found")
                        + properties.getString("pathFinder.return_empty_path"));
                return Path.of("");
            }
        } catch (URISyntaxException | IOException exp) {
            logger.error(exp.getMessage());
            logger.warn(properties.getString("pathFinder.return_empty_path"));
            return Path.of("");
        }
    }
}

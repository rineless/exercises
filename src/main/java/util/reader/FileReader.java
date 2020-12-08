package util.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader extends Reader {
    private final Logger logger = LogManager.getLogger(FileReader.class);
    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg");

    public List<String> receiveLinesAsList(Path path) {
        try {
            if (Objects.nonNull(path)) {
                if (Files.exists(path) && Files.isRegularFile(path)
                        && Files.isReadable(path))
                    return Files.lines(path).collect(Collectors.toList());
                else {
                    throw new IllegalArgumentException(properties.getString("fileReader.not_found_readable"));
                }
            }
            return new ArrayList<>();
        } catch (IOException | IllegalArgumentException exp) {
            if(exp instanceof IllegalArgumentException)
                logger.error(exp.getMessage());
            logger.warn(properties.getString("fileReader.path_incorrect")
                    + properties.getString("fileReader.create_empty"));
            return new ArrayList<>();
        }
    }

    public List<String> receiveLinesAsList(String path) {
        if (Objects.nonNull(path)){
            logger.warn(properties.getString("fileReader.null_path") + properties.getString("fileReader.create_empty"));
            return new ArrayList<>();
        }

        return receiveLinesAsList(Path.of(path));
    }



}

package util.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.reader.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FileWriter {
    private final Logger logger = LogManager.getLogger(FileWriter.class);
    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg");

    public boolean appendLine(String line, Path path) {
        try {
            if (Objects.nonNull(line) && Objects.nonNull(path)) {
                if (Files.exists(path) && Files.isRegularFile(path)
                        && Files.isWritable(path)) {
                    Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            logger.error(properties.getString("fileWriter.ile_not_found") + e.getMessage());
            return false;
        }
    }

    public boolean deleteLine(String line, Path path){
        try {
            if(Objects.nonNull(line) && Objects.nonNull(path)) {
                if (Files.exists(path) && Files.isRegularFile(path)
                        && Files.isReadable(path) && Files.isWritable(path)) {
                    Files.write(path, Files.lines(path).filter(fileLine -> fileLine.contentEquals(line) ? false : true)
                            .collect(Collectors.toList()));
                    return true;
                }
            }
            return false;
        } catch (IOException exception) {
            logger.error(properties.getString("fileWriter.path_not_found"));
            return false;
        }
    }

    public boolean rewriteLine(String line, int lineNumber, Path path) {
        try {
            if (Objects.nonNull(line) && Objects.nonNull(path)) {
                if (Files.exists(path) && Files.isRegularFile(path)
                        && Files.isReadable(path) && Files.isWritable(path)) {
                    List<String> text = Files.readAllLines(path);
                    text.set(lineNumber, line);
                    Files.write(path, text);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            logger.error(properties.getString("fileWriter.file_not_found"));
            return false;
        } catch (IndexOutOfBoundsException e) {
            logger.error(properties.getString("fileWriter.line_number_incorrect"));
            return false;
        }
    }
}

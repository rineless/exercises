package util.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileWriter {
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
            System.out.println("File not found." + e.getMessage());
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
            System.out.println("Path not found");
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
            System.out.println("File not found");
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Line number is incorrect");
            return false;
        }
    }
}
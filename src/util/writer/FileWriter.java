package util.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriter {
    public void writeLineToFile(String line, Path path) {
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("File not found");
        } catch (NullPointerException e){
            System.out.println("Cannot append null to file");
        }
    }
}

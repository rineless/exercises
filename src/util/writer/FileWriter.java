package util.writer;

import model.student.Student;
import util.reader.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FileWriter {
    public boolean appendLine(String line, Path path) {
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            System.out.println("File not found." + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            System.out.println("Cannot append null to file" + e.getMessage());
            return false;
        }
    }

    public boolean deleteLine(String line, Path path){
        try {
            Files.write(path, Files.lines(path).filter(fileLine -> fileLine.contentEquals(line) ? false : true).collect(Collectors.toList()));
            return true;
        } catch (NullPointerException exception) {
            System.out.println("Illegal argument. Line cannot be null");
            return false;
        } catch (IOException exception) {
            System.out.println("Path not found");
            return false;
        }
    }

    public boolean rewriteLine(String line, int lineNumber, Path path){
        try {
            List<String> text = Files.readAllLines(path);
            text.set(lineNumber, line);
            Files.write(path, text);
            return true;
        } catch (IOException e) {
            System.out.println("File not found");
            return false;
        } catch (IndexOutOfBoundsException e)  {
            System.out.println("Line number is incorrect");
            return false;
        }
    }
}

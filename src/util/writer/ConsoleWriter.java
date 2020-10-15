package util.writer;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleWriter {

    public void printLine(String line){
        System.out.println(line);
    }

    public void printListOfLines(List<String> lines){
        String text = lines.stream().map(line -> line + "\n").collect(Collectors.joining());
        System.out.println(text);
    }

    public void printLineWithMessageAndSeparation(String message, String line){
        System.out.println("-----------------" + message +"-----------------");
        printLine(line);
        System.out.println("----------------------------------");
    }

    public void printLineWithSeparation(String line){
        System.out.println("----------------------------------");
        printLine(line);
        System.out.println("----------------------------------");
    }

    public void printListOfLinesWithNameAndSeparation(String name, List<String> lines){
        System.out.println("-----------------" + name +"-----------------");
        printListOfLines(lines);
        System.out.println("----------------------------------");
    }

    public void printListOfLinesWithSeparation(List<String> lines){
        System.out.println("----------------------------------");
        printListOfLines(lines);
        System.out.println("----------------------------------");
    }
}

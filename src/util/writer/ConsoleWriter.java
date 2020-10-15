package util.writer;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleWriter {

    void printLine(String line){
        System.out.println(line);
    }

    void printListOfLines(List<String> lines){
        String text = lines.stream().map(line -> line + "\n").collect(Collectors.joining());
        System.out.println(text);
    }

    void printListOfLinesWithNameAndSeparation(String name, List<String> lines){
        System.out.println("-----------------" + name +"-----------------");
        printListOfLines(lines);
        System.out.println("----------------------------------");
    }

    void printListOfLinesWithSeparation(List<String> lines){
        System.out.println("----------------------------------");
        printListOfLines(lines);
        System.out.println("----------------------------------");
    }
}

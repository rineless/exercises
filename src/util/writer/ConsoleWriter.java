package util.writer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConsoleWriter {
    private final String separatorPattern;
    private final int separatorLength;
    private final String separator;

    public ConsoleWriter(){
        separatorPattern = "";
        separatorLength = 0;
        separator = calculateSeparator();
    }

    public ConsoleWriter(String separatorPattern, int separatorLength){
        if(Objects.isNull(separatorPattern) || separatorLength < 0) {
            this.separatorPattern = "";
            this.separatorLength = 0;
        }
        else {
            this.separatorPattern = separatorPattern;
            this.separatorLength = separatorLength;
        }
        this.separator = calculateSeparator();
    }

    public void printLine(String line){
        System.out.println(line);
    }

    public void printListOfLines(List<String> lines){
        String text = lines.stream().map(line -> line + "\n").collect(Collectors.joining());
        System.out.println(text);
    }

    public void printHeader(String header){
        System.out.println(receiveHalfOfSeparator() + header +receiveHalfOfSeparator());
    }

    public void printSeparator(){
        System.out.println(separator);
    };

    public void printLineWithHeaderAndSeparation(String header, String line){
        printHeader(header);
        printLine(line);
        printSeparator();
    }

    public void printLineWithSeparation(String line){
        printSeparator();
        printLine(line);
        printSeparator();
    }

    public void printListOfLinesWithMessageAndSeparation(String header, List<String> lines){
        printHeader(header);
        printListOfLines(lines);
        printSeparator();
    }

    public void printListOfLinesWithSeparation(List<String> lines){
        printSeparator();
        printListOfLines(lines);
        printSeparator();
    }


    private String receiveHalfOfSeparator(){
        return separator.substring(0, separator.length()/2);
    }

    private String calculateSeparator(){
        return separatorPattern.repeat(separatorLength);
    }
}

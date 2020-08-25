import model.student.Student;
import util.CSVParser;
import util.FileReader;
import util.SeparatedValuesParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args){
        List<Student> studentList = receiveListOfStudents("src/data/StudentData.csv");
        studentList.stream().forEach(student -> System.out.println(student.toString()));
        list.add("12,Ruby,Roth,f,29.12.1998,Netherlands,Amsterdam,payable,1,present,ruby.roth@gmail.com");
        Files.write();
        /*Student student = new Student();
        String a = "4 06 2098";
        String[] b = a.split(" ");
        for(String x:b){
            System.out.println(x);
        }*/

    }

    public static List<Student> receiveListOfStudents(String path){
        FileReader studentReader = new FileReader();
        List<String> lineList = studentReader.receiveLinesAsList(path);
        CSVParser parser = new CSVParser();
        lineList.remove(0);
        return lineList.stream().map(line -> parser.parseLineToStudent(line)).collect(Collectors.toList());
    }
}

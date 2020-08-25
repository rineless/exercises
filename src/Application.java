import model.group.Group;
import model.student.Student;
import model.student.TypeOfContract;
import util.CSVParser;
import util.FileReader;
import util.SeparatedValuesParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args){
        /*Student student = receiveListOfStudents("src/data/StudentData.csv");
        System.out.println(student.toString());*/
        System.out.println("Students:");
        List<Student> studentList = receiveListOfStudents("src/data/StudentData.csv");
        studentList.stream().forEach(student -> System.out.println(student.toString()));

        System.out.println("Groups:");
        List<Group> groupList = receiveListOfGroups("src/data/StudentGroupData.csv");
        groupList.stream().forEach(group -> System.out.println(group.toString()));

        List<String> studentsToAdd = new ArrayList<>();
        studentsToAdd.add("13,Rubel,Bare,m,15.02.1998,German,Bremen,payable,0,present,rubel.bare@gmail.com");
        addStudent(studentsToAdd,studentList,"src/data/StudentData.csv");


        /*list.add("12,Ruby,Roth,f,29.12.1998,Netherlands,Amsterdam,payable,1,present,ruby.roth@gmail.com");
        Files.write();*/
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

    public static List<Group> receiveListOfGroups(String path){
        FileReader groupReader = new FileReader();
        List<String> lineList = groupReader.receiveLinesAsList(path);
        CSVParser parser = new CSVParser();
        lineList.remove(0);
        return lineList.stream().map(line -> parser.parseLineToGroup(line)).collect(Collectors.toList());
    }

    public static void addStudent(List<String> lines, List<Student> studentList, String path){
        lines.stream().map(line -> new CSVParser().parseLineToStudent(line))
                .forEach(student -> studentList.add(student));
        try {
            Files.write(Path.of(path),lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

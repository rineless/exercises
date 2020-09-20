import model.group.Group;
import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
import util.parser.CSVParser;
import util.reader.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args){
        Path current = Path.of(".");

        System.out.println("Students:");
        List<Student> studentList = receiveListOfStudents(current.toString() + "/src/data/StudentData.csv");
        studentList.stream().forEach(student -> System.out.println(student.toString()));

        System.out.println("Groups:");
        List<Group> groupList = receiveListOfGroups(current.toString() + "/src/data/StudentGroupData.csv");
        groupList.stream().forEach(group -> System.out.println(group.toString()));

        List<Student> studentsToAdd = new ArrayList<>();
        studentsToAdd.add(new Student().setId(13).setName("Rubel").setSurname("Bare").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1998,2,15)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(0).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("rubel.bare@gmail.com"));
        addStudents(studentsToAdd,studentList,current.toString() + "/src/data/StudentData.csv");

        //TODO find what to do with new abbreviations
        /*List<String> groupsToAdd = new ArrayList<>();
        groupsToAdd.add("4,Analysis,Anl,english,no,0,3,15,Karol Maier,karol.maier@myuni.de");
        addGroup(groupsToAdd,groupList,"src/data/StudentGroupData.csv");*/

    }

    public static List<Student> receiveListOfStudents(String path){
        FileReader studentReader = new FileReader();

        return studentReader.receiveLinesAsList(path).stream().skip(1)
                .map(line -> new CSVParser().parseLineToStudent(line)).collect(Collectors.toList());
    }

    public static List<Group> receiveListOfGroups(String path){
        FileReader groupReader = new FileReader();

        return groupReader.receiveLinesAsList(path).stream().skip(1)
                .map(line -> new CSVParser().parseLineToGroup(line)).collect(Collectors.toList());
    }

    public static void addStudents(List<Student> studentsToAdd, List<Student> studentList, String path){
        studentsToAdd.stream().forEach(student -> studentList.add(student));
        try {
            Files.write(Path.of(path), studentsToAdd.stream()
                            .map(student -> new CSVParser().parseStudentToLine(student)).collect(Collectors.toList())
                    , StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //TODO check, finalize
    public static void addGroup(List<String> lines, List<Group> groupList, String path){
        lines.stream().map(line -> new CSVParser().parseLineToGroup(line))
                .forEach(group -> groupList.add(group));
        try {
            Files.write(Path.of(path),lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

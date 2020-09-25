package repository;

import model.student.Student;
import util.parser.CSVParser;
import util.reader.FileReader;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CSVStudentsRepository implements StudentsRepository{
    List<Student> studentData;
    String studentDataPath;

    public List<Student> getAll() {

        if (studentData == null) {
            URL url = getClass().getClassLoader().getResource("data/StudentData.csv");
            if(url == null)
                throw new IllegalArgumentException("Cannot find StudentData.csv file");
            studentDataPath = new File(url.getPath()).toString();

            studentData = new FileReader().receiveLinesAsList(studentDataPath).stream().skip(1)
                    .map(line -> new CSVParser().parseLineToStudent(line)).collect(Collectors.toList());
        }

        return studentData.stream().collect(Collectors.toList());
     }

     public Student getById(int id){
        try {
            return studentData.stream()
                    .filter(student -> student.getId() == id).findFirst().get();
        }
        catch(NullPointerException exception){
            System.out.println("Student not found");
            return null;
        }
     }

    public void add(Student student) throws IllegalArgumentException{
        if (studentData == null) {
            throw new IllegalArgumentException("Student repository is not created yet. Cannot add new Student to null list.");
        }
        if (student != null) {

            if (student.getName() != null & student.getSurname() != null & student.getGender() != null
                    & student.getBirthDate() != null & student.getCitizenship() != null & student.getPlaceOfBirth() != null
                    & student.getTypeOfContract() != null & student.getTypeOfStudying() != null & student.getContactInformation() != null) {

                try {
                    Files.write(Path.of(studentDataPath), (new CSVParser().parseStudentToLine(student) + "\n").getBytes()
                            , StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                studentData.add(student);

            }
        }
    }

    public void update() {
        if (studentData == null) {
            throw new IllegalArgumentException("Student repository is not created yet. Cannot update null list");
        }

        new FileReader().receiveLinesAsList(studentDataPath).stream().skip(1)
                .map(line -> new CSVParser().parseLineToStudent(line))
                .filter(student -> {
                    for(Student studentFromCSVFile: studentData){
                        if(studentFromCSVFile.equals(student))
                            return false;
                    }
                    return true;
                }).forEach(student -> studentData.add(student));


    }

    public void delete(Student student){

        if (studentData == null) {
            throw new IllegalArgumentException("Student repository is not created yet. Cannot delete Student from null list.");
        }

        try {
            int id = student.getId();
            //TODO

            Student foundStudent = studentData.stream().filter(studentFromDatabase -> studentFromDatabase.equals(student))
                    .findFirst().get();
            studentData.remove(foundStudent);
        }
        catch (NoSuchElementException | NullPointerException exception){
        }
    }
}

package repository;

import exceptions.ObjectsOverflowException;
import model.student.Student;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.reader.Reader;

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
    private static int count;

    private List<Student> studentData;
    private final FileReader reader;
    private final ILineParser parser;
    private final Path studentDataPath;

    public CSVStudentsRepository() throws ObjectsOverflowException{
        if(count>0)
            throw new ObjectsOverflowException("Student repository can be created only once");

        reader = new FileReader();
        parser = new CSVParser();
        studentDataPath = reader.findAbsolutePathFromRelativeToResourceFolder("data/StudentData.csv");
        ++count;

    }

    public List<Student> getAll() {

        if (studentData == null) {

            studentData = reader.receiveLinesAsList(studentDataPath).stream().skip(1)
                    .map(line -> parser.parseLineToStudent(line)).collect(Collectors.toList());
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
                    Files.write(studentDataPath, (parser.parseStudentToLine(student) + "\n").getBytes()
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

        reader.receiveLinesAsList(studentDataPath).stream().skip(1)
                .map(line -> parser.parseLineToStudent(line))
                .filter(student -> {
                    for(Student studentFromCSVFile: studentData){
                        if(studentFromCSVFile.equals(student))
                            return false;
                    }
                    return true;
                }).forEach(student -> studentData.add(student));


    }

    public void delete(Student student) {

        if (studentData == null) {
            throw new IllegalArgumentException("Student repository is not created yet. Cannot delete Student from null list.");
        }

        try {

            Student foundStudent = studentData.stream().filter(studentFromDatabase -> studentFromDatabase.equals(student))
                    .findFirst().get();
            studentData.remove(foundStudent);

            Files.write(studentDataPath, studentData.stream()
                    .map(eachStudent -> parser.parseStudentToLine(eachStudent)).collect(Collectors.toList()));
        } catch (NoSuchElementException | NullPointerException exception) {
            System.out.println("Student not found. Cannot delete");
        } catch (IOException exception) {
            System.out.println("Path not found");
        }
    }
}

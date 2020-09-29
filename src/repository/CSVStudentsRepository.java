package repository;

import model.student.Student;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CSVStudentsRepository implements StudentsRepository {

    private final FileReader reader;
    private final ILineParser parser;
    private final String studentDataPath = "data/StudentData.csv";

    public CSVStudentsRepository() {
        reader = new FileReader();
        parser = new CSVParser();

    }

    public List<Student> getAll() {

        List<Student> studentData = reader.receiveLinesAsList(studentDataPath).stream().skip(1)
                .map(line -> parser.parseLineToStudent(line)).collect(Collectors.toList());

        return studentData.stream().collect(Collectors.toList());
    }

    public Student getById(int id) {
        Student student = null;
        Optional<Student> firstElement = this.getAll().stream()
                .filter(s -> s.getId() == id).findFirst();
        if (firstElement.isPresent()) {
            student = firstElement.get();
        } else {
            System.out.println("Student not found");
        }
        return student;
    }

    public void add(Student student) throws IllegalArgumentException{
        if (student != null) {

            if (student.getName() != null & student.getSurname() != null & student.getGender() != null
                    & student.getBirthDate() != null & student.getCitizenship() != null & student.getPlaceOfBirth() != null
                    & student.getTypeOfContract() != null & student.getTypeOfStudying() != null & student.getContactInformation() != null) {//TODO please follow the Single Responsibility principle and move the validation to an appropriate class

                try {
                    Files.write(studentDataPath, (parser.parseStudentToLine(student) + "\n").getBytes()
                            , StandardOpenOption.APPEND);  //TODO move this code to FileReader and rename it to thr File ReaderWriter
                } catch (IOException e) {
                    e.printStackTrace();
                }

                studentData.add(student);

            }
        }
    }

        public void update () {
            if (studentData == null) {
                throw new IllegalArgumentException("Student repository is not created yet. Cannot update null list");
            }

            reader.receiveLinesAsList(studentDataPath).stream().skip(1)
                    .map(line -> parser.parseLineToStudent(line))
                    .filter(student -> {
                        for (Student studentFromCSVFile : studentData) {
                            if (studentFromCSVFile.equals(student))
                                return false;
                        }
                        return true;
                    }).forEach(student -> studentData.add(student));


        }

        public void delete (Student student){

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

package repository;

import model.student.Student;
import util.finder.PathFinder;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.validation.StudentValidation;
import util.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class CSVStudentsRepository implements StudentsRepository {

    private final FileReader reader;
    private final FileWriter writer;
    private final ILineParser parser;
    private final String studentDataPath = "data/StudentData.csv";

    public CSVStudentsRepository() {
        reader = new FileReader();
        writer = new FileWriter();
        parser = new CSVParser();

    }

    public List<Student> getAll() {

        List<Student> studentData = reader.receiveLinesAsList(PathFinder.findFromResources(studentDataPath)).stream().skip(1)
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

    public void add(Student student) throws IllegalArgumentException {
        if (student != null) {

            if (StudentValidation.isValid(student)) {//DONE please follow the Single Responsibility principle and move the validation to an appropriate class

                writer.appendLine(parser.parseStudentToLine(student) + "\n", PathFinder.findFromResources(studentDataPath));//DONE move this code to FileReader and rename it to thr File ReaderWriter

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
                    for (Student studentFromCSVFile : studentData) {
                        if (studentFromCSVFile.equals(student))
                            return false;
                    }
                    return true;
                }).forEach(student -> studentData.add(student));


    }

    public void delete(Student student) {
        writer.deleteLine(parser.parseStudentToLine(student), PathFinder.findFromResources(studentDataPath));
    }
}

package repository;

import additional.StudentGroupSource;
import model.student.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import util.finder.PathFinder;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.writer.FileWriter;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.validateMockitoUsage;

@ExtendWith(MockitoExtension.class)
public class CSVStudentsRepositoryTest {
    static Path repository = PathFinder.findFromResources("data/StudentData.csv");
    List<String> lines;

    @Mock
    FileReader mockReader;

    @Mock
    FileWriter mockWriter;

    @Mock
    ILineParser mockParser;


    @BeforeEach
    public void prepareMock() {
        lines = new LinkedList<>();
        lines.add("ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information");
        lines.add(StudentGroupSource.existingStudentID1);
        lines.add(StudentGroupSource.existingStudentID2);
        lines.add(StudentGroupSource.existingStudentID3);
        lenient().when(mockReader.receiveLinesAsList(repository)).thenReturn(lines);

        lenient().when(mockParser
                .parseLineToStudent(StudentGroupSource.existingStudentID1))
                .thenReturn(StudentGroupSource.existingStudentID1());
        lenient().when(mockParser
                .parseLineToStudent(StudentGroupSource.existingStudentID2))
                .thenReturn(StudentGroupSource.existingStudentID2());
        lenient().when(mockParser
                .parseLineToStudent(StudentGroupSource.existingStudentID3))
                .thenReturn(StudentGroupSource.existingStudentID3());

    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @DisplayName("List of all students should be returned")
    public void getAll_ShouldReturnAllStudentsFromRepository() {

        List<Student> expected = new LinkedList<>();
        expected.add(StudentGroupSource.existingStudentID1());
        expected.add(StudentGroupSource.existingStudentID2());
        expected.add(StudentGroupSource.existingStudentID3());

        List<Student> actual = new CSVStudentsRepository(mockReader, mockWriter, mockParser).getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all students");
    }

    @Test
    @DisplayName("Student with inputted id should be returned")
    public void getById_ShouldReturnStudent() {

        Student expected = StudentGroupSource.existingStudentID2();

        Student actual = new CSVStudentsRepository(mockReader, mockWriter, mockParser).getById(2);

        Assertions.assertEquals(expected, actual, "Expected returned student with inputted id");
    }

    @Test
    @DisplayName("Empty student should be returned if student not found")
    public void getById_WithNotExistingStudentId_ShouldReturnNull() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        Student actual = studentsRepository.getById(5);

        Assertions.assertNull(actual, "Expected null by not existing student id input");
    }

    @Test
    @DisplayName("Student should be added to repository")
    public void addStudent_ShouldAddStudentToRepository() {
        Mockito.when(mockParser.parseStudentToLine(StudentGroupSource.notExistingStudentID4()))
                .thenReturn(StudentGroupSource.notExistingStudent);
        Mockito.when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.add(StudentGroupSource.notExistingStudentID4());

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected adding student to repository. " +
                "FileWriter appendLine method should be called with repository and line input: \\n" + StudentGroupSource.notExistingStudent))
                .appendLine("\n" + StudentGroupSource.notExistingStudent
                        , repository);
    }

    @Test
    @DisplayName("Null should not be added to repository")
    public void addStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.add(null);
        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository." +
                " FileWriter for appending line wont be used."))
                .appendLine(Mockito.any(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("NonValid student should not be added to repository")
    public void addStudent_WithNotValidStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.add(StudentGroupSource.notValidStudentID4());

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student unchanged repository. " +
                "FileWriter method for appending line won't be used. "))
                .appendLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("Student information should be updated")
    public void updateStudent_ShouldUpdateStudentInformation() {
        Mockito.when(mockParser.parseStudentToLine(StudentGroupSource.updatedStudentID2()))
                .thenReturn(StudentGroupSource.updatedStudentID2);
        lenient().when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(StudentGroupSource.updatedStudentID2());

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected updating existing student in repository. " +
                "FileWriter should rewrite 2. line in repository to: " + StudentGroupSource.updatedStudentID2))
                .rewriteLine(StudentGroupSource.updatedStudentID2, 2, repository);

    }

    @Test
    @DisplayName("Not existing student cannot be updated")
    public void updateStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(StudentGroupSource.notExistingStudentID4());

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not existing student input unchanged repository. " +
                "FileWriter won't be used for rewriting line."))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("Null cannot be updated")
    public void updateStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(null);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository. " +
                "FileWriter won't be used for rewriting line."))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));

    }

    @Test
    @DisplayName(" By not valid student input, student cannot be updated")
    public void updateStudent_WithNotValidStudent_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(StudentGroupSource.notValidStudentID2());

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter rewrite method won't be called for repository. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Student should be deleted from repository")
    public void deleteStudent_ShouldDeleteStudentFromRepository() {
        lenient().when(mockWriter.deleteLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        Mockito.when(mockParser.parseStudentToLine(StudentGroupSource.existingStudentID2()))
                .thenReturn(StudentGroupSource.existingStudentID2);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(StudentGroupSource.existingStudentID2());

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected adding student to repository. " +
                "FileWriter deleteLine method will be called with repository and line input: " + StudentGroupSource.existingStudentID2))
                .deleteLine(StudentGroupSource.existingStudentID2, repository);
    }

    @Test
    @DisplayName("Null cannot be deleted from repository")
    public void deleteStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(null);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository. " +
                "FileWriter deleteLine method won't be called for repository. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("By not valid student input nothing should be deleted from repository")
    public void deleteStudent_WithNotValidStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(StudentGroupSource.notValidStudentID2());

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter deleteLine method won't be called for repository. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("By not existing student input nothing should be deleted from repository")
    public void deleteStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        Mockito.when(mockParser.parseStudentToLine(StudentGroupSource.notExistingStudentID4()))
                .thenReturn(StudentGroupSource.notExistingStudent);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(StudentGroupSource.notExistingStudentID4());

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by not existing student input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: " + StudentGroupSource.notExistingStudent))
                .deleteLine(StudentGroupSource.notExistingStudent, repository);

    }

    @Test
    @DisplayName("By student with some modified data input nothing should be deleted from repository")
    public void deleteStudent_WithStudentWithModifiedDataInput_ShouldChangeNothingInRepository() {
        Mockito.when(mockParser.parseStudentToLine(StudentGroupSource.updatedStudentID2()))
                .thenReturn(StudentGroupSource.updatedStudentID2);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(StudentGroupSource.updatedStudentID2());

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by student with some modified data input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: " + StudentGroupSource.updatedStudentID2))
                .deleteLine(StudentGroupSource.updatedStudentID2, repository);

    }
}

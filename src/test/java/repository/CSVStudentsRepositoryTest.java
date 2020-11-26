package repository;

import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
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
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.validateMockitoUsage;

@ExtendWith(MockitoExtension.class)
public class CSVStudentsRepositoryTest {
    static Path repository = PathFinder.findFromResources("data/StudentData.csv");
    List<String> lines;
    Student existingStudent;
    Student notValidStudent;

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
        lines.add("1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com");
        lines.add("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        lines.add("3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com");
        lenient().when(mockReader.receiveLinesAsList(repository)).thenReturn(lines);

        lenient().when(mockParser
                .parseLineToStudent("1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com"))
                .thenReturn(new Student().setId(1).setName("Anna").setSurname("Allen").setGender(Gender.FEMALE)
                        .setBirthDate(LocalDate.of(1998, 11, 7)).setCitizenship("German").setPlaceOfBirth("Hamburg")
                        .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT)
                        .setContactInformation("anna.allen98@gmail.com"));
        lenient().when(mockParser
                .parseLineToStudent("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .thenReturn(new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                        .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                        .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                        .setContactInformation("petter99tailor@gmail.com"));
        lenient().when(mockParser
                .parseLineToStudent("3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com"))
                .thenReturn(new Student().setId(3).setName("Alice").setSurname("Cook").setGender(Gender.FEMALE)
                        .setBirthDate(LocalDate.of(1999, 4, 14)).setCitizenship("Ungarn").setPlaceOfBirth("Paks")
                        .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.ONLINE)
                        .setContactInformation("alicook@gmail.com"));

    }

    @BeforeEach
    public void initStudent(){
        existingStudent = new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com");
        notValidStudent = new Student().setId(2).setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com");

    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    @DisplayName("List of all students should be returned")
    public void getAll_ShouldReturnAllStudentsFromRepository() {

        List<Student> expected = new LinkedList<>();
        expected.add(new Student().setId(1).setName("Anna").setSurname("Allen").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 11, 7)).setCitizenship("German").setPlaceOfBirth("Hamburg")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("anna.allen98@gmail.com"));
        expected.add(new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com"));
        expected.add(new Student().setId(3).setName("Alice").setSurname("Cook").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1999, 4, 14)).setCitizenship("Ungarn").setPlaceOfBirth("Paks")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("alicook@gmail.com"));

        List<Student> actual = new CSVStudentsRepository(mockReader, mockWriter, mockParser).getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all students");
    }

    @Test
    @DisplayName("Student with inputted id should be returned")
    public void getById_ShouldReturnStudent() {

        Student expected = existingStudent;

        Student actual = new CSVStudentsRepository(mockReader, mockWriter, mockParser).getById(2);

        Assertions.assertEquals(expected, actual, "Expected returned student with inputted id");
    }

    @Test
    @DisplayName("Empty student should be returned if student not found")
    public void getById_WithNotExistingStudentId_ShouldReturnNull(){
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        Student actual = studentsRepository.getById(5);

        Assertions.assertNull(actual, "Expected null by not existing student id input");
    }

   @Test
    @DisplayName("Student should be added to repository")
    public void addStudent_ShouldAddStudentToRepository() {
       Mockito.when(mockParser.parseStudentToLine(existingStudent.setId(4)))
               .thenReturn("4,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
       Mockito.when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
       CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

       studentsRepository.add(existingStudent.setId(4));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected adding student to repository. " +
                "FileWriter appendLine method should be called with repository and line input: \\n4,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .appendLine("\n4,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"
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

        studentsRepository.add(notValidStudent.setId(4));

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student unchanged repository. " +
                "FileWriter method for appending line won't be used. "))
                .appendLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("Student information should be updated")
    public void updateStudent_ShouldUpdateStudentInformation() {
        Mockito.when(mockParser.parseStudentToLine(existingStudent.setName("Bob")))
                .thenReturn("2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        lenient().when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(existingStudent.setName("Bob"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected updating existing student in repository. " +
                "FileWriter should rewrite 2. line in repository to: 2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com "))
                .rewriteLine("2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com",2,repository);

    }

    @Test
    @DisplayName("Not existing student cannot be updated")
    public void updateStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(existingStudent.setId(9));

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
    public void updateStudent_WithNotValidStudent_ShouldChangeNothingInRepository(){
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.update(notValidStudent);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter rewrite method won't be called for repository. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Student should be deleted from repository")
    public void deleteStudent_ShouldDeleteStudentFromRepository(){
        lenient().when(mockWriter.deleteLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        Mockito.when(mockParser.parseStudentToLine(existingStudent))
                .thenReturn("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(existingStudent);

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected adding student to repository. " +
                "FileWriter deleteLine method will be called with repository and line input: 2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com", repository);
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

        studentsRepository.delete(notValidStudent);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter deleteLine method won't be called for repository. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("By not existing student input nothing should be deleted from repository")
    public void deleteStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        Mockito.when(mockParser.parseStudentToLine(existingStudent.setId(7)))
                .thenReturn("7,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(existingStudent.setId(7));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by not existing student input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: 7,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("7,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com", repository);

    }

    @Test
    @DisplayName("By student with some modified data input nothing should be deleted from repository")
    public void deleteStudent_WithStudentWithModifiedDataInput_ShouldChangeNothingInRepository() {
        Mockito.when(mockParser.parseStudentToLine(existingStudent.setName("Bob")))
                .thenReturn("2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter, mockParser);

        studentsRepository.delete(existingStudent.setName("Bob"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by student with some modified data input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: 2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"
                        , repository);

    }
}

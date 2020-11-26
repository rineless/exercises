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
import util.reader.FileReader;
import util.writer.FileWriter;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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


    @BeforeEach
    public void prepareMock() {
        lines = new LinkedList<>();
        lines.add("ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information");
        lines.add("1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com");
        lines.add("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        lines.add("3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com");
        lenient().when(mockReader.receiveLinesAsList(repository)).thenReturn(lines);



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

        List<Student> actual = new CSVStudentsRepository(mockReader, mockWriter).getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all students");
    }

    @Test
    @DisplayName("Student with inputted id should be returned")
    public void getById_ShouldReturnStudent() {

        Student expected = new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com");

        Student actual = new CSVStudentsRepository(mockReader, mockWriter).getById(2);

        Assertions.assertEquals(expected, actual, "Expected returned student with inputted id");
    }

    @Test
    @DisplayName("Empty student should be returned if student not found")
    public void getById_WithNotExistingStudentId_ShouldReturnNull(){
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        Student actual = studentsRepository.getById(5);

        Assertions.assertNull(actual, "Expected null by not existing student id input");
    }

   @Test
    @DisplayName("Student should be added to repository")
    public void addStudent_ShouldAddStudentToRepository() {
        Mockito.when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);

        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Gerald,Anond,MALE,13.6.1998,German,Dresden,STIPEND,3,ONLINE,gerald.anond@gmail.com\n";

        studentsRepository.add(new Student().setId(4).setName("Gerald").setSurname("Anond").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1998, 6, 13)).setCitizenship("German").setPlaceOfBirth("Dresden")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(3).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("gerald.anond@gmail.com"));

        Mockito.verify(mockWriter, Mockito.times(1)).appendLine("\n4,Gerald,Anond,MALE,13.6.1998,German,Dresden,STIPEND,3,ONLINE" +
                ",gerald.anond@gmail.com", repository);
        lines.add("4,Gerald,Anond,MALE,13.6.1998,German,Dresden,STIPEND,3,ONLINE,gerald.anond@gmail.com");

       String actual = mockReader.receiveLinesAsList(repository).stream().map(line -> line + "\n")
               .collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected adding student to repository");
    }

    @Test
    @DisplayName("Null should not be added to repository")
    public void addStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.add(null);
        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository." +
                " FileWriter for appending line wont be used."))
                .appendLine(Mockito.any(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("NonValid student should not be added to repository")
    public void addStudent_WithNotValidStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.add(new Student().setId(4).setSurname("Anond").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1998, 6, 13)).setCitizenship("German").setPlaceOfBirth("Dresden")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(3).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("gerald.anond@gmail.com"));

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student unchanged repository. " +
                "FileWriter method for appending line won't be used. "))
                .appendLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("Student information should be updated")
    public void updateStudent_ShouldUpdateStudentInformation() {
        lenient().when(mockWriter.appendLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.update(new Student().setId(2).setName("Pettery").setSurname("Trailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("pettery99trailor@gmail.com"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected updating existing student in repository. " +
                "FileWriter should rewrite 2. line in repository to: 2,Pettery,Trailor,MALE,25.6.1999,German,Bremen,PAYABLE,1,PRESENT,pettery99trailor@gmail.com "))
                .rewriteLine("2,Pettery,Trailor,MALE,25.6.1999,German,Bremen,PAYABLE,1" +
                        ",PRESENT,pettery99trailor@gmail.com",2,repository);

    }

    @Test
    @DisplayName("Not existing student cannot be updated")
    public void updateStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.update(new Student().setId(7).setSurname("Anond").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1998, 6, 13)).setCitizenship("German").setPlaceOfBirth("Dresden")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(3).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("gerald.anond@gmail.com"));

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not existing student input unchanged repository. " +
                "FileWriter won't be used for rewriting line."))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("Null cannot be updated")
    public void updateStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.update(null);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository. " +
                "FileWriter won't be used for rewriting line."))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));

    }

    @Test
    @DisplayName(" By not valid student input, student cannot be updated")
    public void updateStudent_WithNotValidStudent_ShouldChangeNothingInRepository(){
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.update(new Student().setId(2).setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1998, 6, 13)).setCitizenship("German").setPlaceOfBirth("Dresden")
                .setGroupId(3).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("gerald.anond@gmail.com"));

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter rewrite method won't be called for repository. "))
                .rewriteLine(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(repository));
    }

    @Test
    @DisplayName("Student should be deleted from repository")
    public void deleteStudent_ShouldDeleteStudentFromRepository(){
        lenient().when(mockWriter.deleteLine(Mockito.anyString(), Mockito.eq(repository))).thenReturn(true);
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.delete(new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected adding student to repository. " +
                "FileWriter deleteLine method will be called with repository and line input: 2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com", repository);
    }

    @Test
    @DisplayName("Null cannot be deleted from repository")
    public void deleteStudent_WithNullInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.delete(null);

        Mockito.verify(mockWriter, Mockito.never().description("Expected by null input unchanged repository. " +
                "FileWriter deleteLine method won't be called for repository. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("By not valid student input nothing should be deleted from repository")
    public void deleteStudent_WithNotValidStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.delete(new Student().setId(2).setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com"));

        Mockito.verify(mockWriter, Mockito.never().description("Expected by not valid student input unchanged repository. " +
                "FileWriter deleteLine method won't be called for repository. "))
                .deleteLine(Mockito.anyString(), Mockito.eq(repository));

    }

    @Test
    @DisplayName("By not existing student input nothing should be deleted from repository")
    public void deleteStudent_WithNotExistingStudentInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.delete(new Student().setId(7).setName("Tailor").setSurname("Jh").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setTypeOfStudying(TypeOfStudying.PRESENT).setGroupId(2)
                .setContactInformation("petter99tailor@gmail.com"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by not existing student input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: 7,Tailor,Jh,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("7,Tailor,Jh,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com", repository);

    }

    @Test
    @DisplayName("By student with some modified data input nothing should be deleted from repository")
    public void deleteStudent_WithStudentWithModifiedDataInput_ShouldChangeNothingInRepository() {
        CSVStudentsRepository studentsRepository = new CSVStudentsRepository(mockReader, mockWriter);

        studentsRepository.delete(new Student().setId(2).setName("Peterry").setSurname("Tailorre").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 7, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com"));

        Mockito.verify(mockWriter, Mockito.times(1).description("Expected by student with some modified data input unchanged repository. " +
                "FileWriter deleteLine method should be called with repository and line input: 2,Peterry,Tailorre,MALE,25.7.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"))
                .deleteLine("2,Peterry,Tailorre,MALE,25.7.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com"
                        , repository);

    }
}

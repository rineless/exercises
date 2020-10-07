package repository;

import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
import org.junit.jupiter.api.*;
import util.finder.PathFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CSVStudentsRepositoryTest {
    static Path repository = PathFinder.findFromResources("data/StudentData.csv");

    @BeforeAll
    public static void renameOriginalRepository() throws IOException { //TODO exception
        Files.move(repository, repository.resolveSibling("OStudentData.csv"));
    }

    @BeforeEach
    public void createTestRepository() throws IOException { //TODO exception
        Files.createFile(repository);
        List<String> lines = new LinkedList<>();
        lines.add("ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information");
        lines.add("1,Anna,Allen,f,07.11.1998,German,Hamburg,stipend,1,present,anna.allen98@gmail.com");
        lines.add("2,Peter,Tailor,m,25.06.1999,German,Bremen,payable,2,present,petter99tailor@gmail.com");
        lines.add("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com");
        Files.write(repository, lines);
    }

    @AfterEach
    public void deleteTestRepository() throws IOException { //TODO exception
        Files.delete(repository);
    }

    @Test
    @DisplayName("List of all students should be returned")
    public void getAll_ShouldReturnAllStudentsFromRepository(){
        List<Student> expected = new LinkedList<>();
        expected.add(new Student().setId(1).setName("Anna").setSurname("Allen").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998,11,7)).setCitizenship("German").setPlaceOfBirth("Hamburg")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("anna.allen98@gmail.com"));
        expected.add(new Student().setId(2).setName("Peter").setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999,6,25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com"));
        expected.add(new Student().setId(3).setName("Alice").setSurname("Cook").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1999,4,14)).setCitizenship("Ungarn").setPlaceOfBirth("Paks")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("alicook@gmail.com"));

        List<Student> actual = new CSVStudentsRepository().getAll();

        Assertions.assertIterableEquals(expected, actual, "Expected returned list of all students");
    }

    @AfterAll
    public static void returnOriginalRepositoryName() throws IOException { //TODO exception
        Path originalRepository = PathFinder.findFromResources("data/OStudentData.csv");
        Files.move(originalRepository, originalRepository.resolveSibling("StudentData.csv"));
    }
}

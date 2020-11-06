package service;

import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
import org.junit.jupiter.api.*;
import util.finder.PathFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsServiceTest {
    Path studentRepository = PathFinder.findFromResources("data/StudentData.csv");
    static Path groupRepository = PathFinder.findFromResources("data/StudentGroupData.csv");

    @BeforeAll
    public static void fillRepositoryWithGroups() throws IOException {
        List<String> lines = new LinkedList<>();
        lines.add("ID,Group name,Language,Online access,max_Attendees(Present),Responsible for Group,Contact information");
        lines.add("1,DS,germany,true,1,Adam Becker,adam.becker@myuni.de");
        lines.add("2,Alg,english,true,3,Shon Braun,shon.braun@myuni.de");
        lines.add("3,Anl,english,false,2,Karol Maier,karol.maier@myuni.de");
        Files.write(groupRepository, lines);
    }

    @AfterAll
    public static void clearGroupRepository() throws IOException {
        Files.write(groupRepository, "".getBytes());
    }

    @BeforeEach
    public void fillRepositoryWithStudentsData() throws IOException {
        List<String> lines = new LinkedList<>();
        lines.add("ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information");
        lines.add("1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com");
        lines.add("2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com");
        lines.add("3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com");
        lines.add("4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com");
        lines.add("5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com");
        lines.add("6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com");
        Files.write(studentRepository, lines);
    }


    @AfterEach
    public void clearRepository() throws IOException {
        Files.write(studentRepository, "".getBytes());
    }

    @Test
    @DisplayName("Student should be added to repository")
    public void shouldAddStudent() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n" +
                "7,Mary,Grind,FEMALE,15.8.1998,German,Berlin,PAYABLE,2,PRESENT,mary.grind@gmail.com\n";

        new StudentsService().add(new Student().setId(7).setName("Mary").setSurname("Grind").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 8, 15)).setCitizenship("German").setPlaceOfBirth("Berlin")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("mary.grind@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected adding student to repository");
    }

    @Test
    @DisplayName("Attendee should not be added to full group")
    public void addStudent_withRequestForFullGroupAdding_shouldDeclineAttendee() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n";

        new StudentsService().add(new Student().setId(7).setName("Mary").setSurname("Grind").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 8, 15)).setCitizenship("German").setPlaceOfBirth("Berlin")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(3).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("mary.grind@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by attendee request to full group unchanged repository");
    }

    @Test
    @DisplayName("Online student can be added even if group is full")
    public void addStudent_withRequestForFullGroupAdding_shouldAddOnlineStudent() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n" +
                "7,Mary,Grind,FEMALE,15.8.1998,German,Berlin,PAYABLE,3,ONLINE,mary.grind@gmail.com\n";;

        new StudentsService().add(new Student().setId(7).setName("Mary").setSurname("Grind").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 8, 15)).setCitizenship("German").setPlaceOfBirth("Berlin")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(3).setTypeOfStudying(TypeOfStudying.ONLINE).setContactInformation("mary.grind@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected adding online student (to full group) to repository");
    }

    @Test
    @DisplayName("Student with already existing id input should not be added")
    public void addStudent_withExistingId_shouldDeclineAdding() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n";

        new StudentsService().add(new Student().setId(5).setName("Mary").setSurname("Grind").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 8, 15)).setCitizenship("German").setPlaceOfBirth("Berlin")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("mary.grind@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by student with already existing id input unchanged repository");
    }

    @Test
    @DisplayName("Attendee with group change into not full group should be updated")
    public void updateStudent_withChangingToNotFullGroup_shouldUpdateAttendee() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,2,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n";

        new StudentsService().update(new Student().setId(5).setName("Alphonse").setSurname("Mason").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(2000, 3, 17)).setCitizenship("Switzerland").setPlaceOfBirth("Brig")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("alph05mason@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by attendee request to not full group updated student");
    }

    @Test
    @DisplayName("Attendee with group change into full group should not be updated")
    public void updateStudent_withChangingToFullGroup_shouldDeclineUpdateForAttendee() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n";

        new StudentsService().update(new Student().setId(1).setName("Anna").setSurname("Allen").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 11, 7)).setCitizenship("German").setPlaceOfBirth("Hamburg")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(3).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("anna.allen98@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by attendee request to full group unchanged repository");
    }

    @Test
    @DisplayName("Student with type studying to present should not be updated if current group is full")
    public void updateStudent_withChangingToPresentStudying_butGroupIsFull_shouldDeclineUpdate() throws IOException {
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n";

        new StudentsService().update(new Student().setId(3).setName("Alice").setSurname("Cook").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1999, 4, 14)).setCitizenship("Ungarn").setPlaceOfBirth("Paks")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("della.fox@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by student request to present type of studying unchanged repository if current group is full");
    }

    @Test
    @DisplayName("Student with type studying to present should be updated if current group is not full")
    public void updateStudent_withChangingToPresentStudying_butGroupIsNotFull_shouldUpdateStudent() throws IOException {
        Files.write(studentRepository, "7,Mary,Grind,FEMALE,15.8.1998,German,Berlin,PAYABLE,2,ONLINE,mary.grind@gmail.com".getBytes(), StandardOpenOption.APPEND);
        String expected = "ID,Name,Surname,Gender,Birth date,Citizenship,Place of Birth,Type of contract,Group ID,Type of Studying,Contact information\n" +
                "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com\n" +
                "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com\n" +
                "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com\n" +
                "4,Della,Fox,FEMALE,5.12.1998,German,Bochum,PAYABLE,3,PRESENT,della.fox@gmail.com\n" +
                "5,Alphonse,Mason,MALE,17.3.2000,Switzerland,Brig,STIPEND,3,PRESENT,alph05mason@gmail.com\n" +
                "6,Kenneth,Meier,MALE,24.9.1999,German,Hamburg,STIPEND,2,PRESENT,kenneth_meier@gmail.com\n" +
                "7,Mary,Grind,FEMALE,15.8.1998,German,Berlin,PAYABLE,2,PRESENT,mary.grind@gmail.com\n";

        new StudentsService().update(new Student().setId(7).setName("Mary").setSurname("Grind").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 8, 15)).setCitizenship("German").setPlaceOfBirth("Berlin")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(2).setTypeOfStudying(TypeOfStudying.PRESENT).setContactInformation("mary.grind@gmail.com"));
        String actual = Files.lines(studentRepository).map(line -> line + "\n").collect(Collectors.joining());

        Assertions.assertEquals(expected, actual, "Expected by student request to present type of studying updated student if current group is not full");
    }
}

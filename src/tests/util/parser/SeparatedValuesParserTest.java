package util.parser;

import model.group.Abbreviation;
import model.group.Group;
import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Locale;

public class SeparatedValuesParserTest {
    @Nested
    @DisplayName("Test parseLineToArray method")
    class ParseLineToArrayTests {

        @ParameterizedTest
        @CsvSource({"_,just_check_ordinary_statement", "-,just-check-ordinary-statement"
                , "\\|,just|check|ordinary|statement"
                , "-\\|\\|tr\\*,just-||tr*check-||tr*ordinary-||tr*statement"})
        @DisplayName("Ordinary line with some separator should be parsed to array")
        void shouldParseOrdinaryLineToArray(String separator_regex, String line) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{"just", "check", "ordinary", "statement"};

            String[] actual = parser.parseLineToArray(line);

            Assertions.assertArrayEquals(expected, actual, "Expected parsed line into array");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Empty line should be parsed to empty array")
        void parseLineToArray_ShouldParseEmptyLineToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{};

            String[] actual = parser.parseLineToArray("");

            Assertions.assertArrayEquals(expected, actual, "Expected parsed empty line into empty array");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Null should be parsed to empty array")
        void parseLineToArray_ShouldParseNullToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String[] expected = new String[]{};

            String[] actual = parser.parseLineToArray(null);

            Assertions.assertArrayEquals(expected, actual, "Expected parsed null into empty array");
        }
    }

    @Nested
    @DisplayName("Test parseArrayToLine method")
    class ParseArrayToLineTests {

        @ParameterizedTest
        @CsvSource({"_,just_check_ordinary_statement", "-,just-check-ordinary-statement"
                , "\\|,just|check|ordinary|statement"
                , "-\\|\\|tr\\*,just-||tr*check-||tr*ordinary-||tr*statement"})
        @DisplayName("Array should be parsed to line with some separator")
        void shouldParseArrayToLine(String separator_regex, String expectedLine) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);

            String actual = parser.parseArrayToLine(new String[]{"just", "check", "ordinary", "statement"});

            Assertions.assertEquals(expectedLine, actual, "Expected parsed array into line");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Empty array should be parsed to empty line")
        void parseArrayToLine_ShouldParseEmptyArrayToEmptyLine(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String expected = "";

            String actual = parser.parseArrayToLine(new String[]{});

            Assertions.assertEquals(expected, actual, "Expected parsed empty array into empty line");
        }

        @ParameterizedTest
        @ValueSource(strings = {",", " ", "\\.\\.", "_", "--"})
        @DisplayName("Null should be parsed to empty line")
        void parseArrayToLine_ShouldParseNullToEmptyArray(String separator_regex) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            String expected = "";

            String actual = parser.parseArrayToLine(null);

            Assertions.assertEquals(expected, actual, "Expected parsed null into empty line");
        }

        @Test
        @DisplayName("Array with some null elements should be parsed to line")
        void parseArrayToLine_WithSomeNullElementsOfArray_ShouldParseToLine(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = "a,,b,c,";

            String actual = parser.parseArrayToLine(new String[]{"a",null,"b","c",null});

            Assertions.assertEquals(expected,actual,"Expected parsed array with some null elements into array");
        }

    }

    @Nested
    @DisplayName("Test parseLineToStudent method")
    class ParseLineToStudentTests{

        @ParameterizedTest
        @CsvSource({"_,3_Alice_Cook_f_14.04.1999_Ungarn_Paks_payable_1_online_alicook@gmail.com"
                , "\\|>\\*>\\|,3|>*>|Alice|>*>|Cook|>*>|f|>*>|14.04.1999" +
                "|>*>|Ungarn|>*>|Paks|>*>|payable|>*>|1|>*>|online|>*>|alicook@gmail.com"
                , ";,3;Alice;Cook;f;14.04.1999;Ungarn;Paks;payable;1;online;alicook@gmail.com"})
        @DisplayName("Line should be parsed into student")
        void shouldParseLineToStudent(String separator_regex, String line) {
            SeparatedValuesParser parser = new SeparatedValuesParser(separator_regex);
            Student expectedStudent = new Student().setId(3).setName("Alice").setSurname("Cook")
                    .setGender(Gender.FEMALE).setBirthDate(LocalDate.of(1999, 4, 14))
                    .setCitizenship("Ungarn").setPlaceOfBirth("Paks").setTypeOfContract(TypeOfContract.PAYABLE)
                    .setGroupId(1).setTypeOfStudying(TypeOfStudying.ONLINE)
                    .setContactInformation("alicook@gmail.com");

            Student actualStudent = parser.parseLineToStudent(line);

            Assertions.assertEquals(expectedStudent, actualStudent, "Should parse line into student");
        }

        @Test
        @DisplayName("Null should throw IllegalArgumentException")
        void parseLineToStudent_WithNullInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent(null), "Expected by null input IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with not enough data should throw IllegalArgumentException")
        void parseLineToStudent_WithNotEnoughDataInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,14.04.1999,Ungarn,Paks,payable,1,alicook@gmail.com")
                    , "Expected by input with not enough data IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing id should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent(",Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing id IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited id should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("three,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with prohibited id IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing name should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingNameInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing name IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing surname should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingSurnameInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,,f,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing surname IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing gender should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingGenderInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing gender IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited gender should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedGenderInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,sdf4,14.04.1999,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with prohibited gender IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing birth date should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingBirthDateInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing birth date IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited birth date should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedBirthDateInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,23.rt.ds,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with prohibited birth date IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing citizenship should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingCitizenshipInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing citizenship IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing place of birth should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingPlaceOfBirthInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing place of birth IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing type of contract should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingTypeOfContractInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,,1,online,alicook@gmail.com")
                    , "Expected by input with missing type of contract IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited type of contract should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedTypeOfContractInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,someContract1,1,online,alicook@gmail.com")
                    , "Expected by input with prohibited type of contract IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing group id should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingGroupIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,,online,alicook@gmail.com")
                    , "Expected by input with missing group id IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited group id should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedGroupIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,someId,online,alicook@gmail.com")
                    , "Expected by input with prohibited group id IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing type of studying should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingTypeOfStudyingInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,,alicook@gmail.com")
                    , "Expected by input with missing type of studying IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with prohibited type of studying should throw IllegalArgumentException")
        void parseLineToStudent_WithProhibitedTypeOfStudyingInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,studying44,alicook@gmail.com")
                    , "Expected by input with prohibited type of studying IllegalArgumentException");
        }

        @Test
        @DisplayName("Input with missing contact information should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingContactInformationInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,")
                    , "Expected by input with missing contact information IllegalArgumentException");
        }


    }

    @Nested
    @DisplayName("Test parseLineToGroup method")
    class ParseLineToGroupTests{

        @Test
        @DisplayName("Line should be parsed to group")
        void shouldParseLineToGroup(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Group expected = new Group().setId(3).setName("Analysis").setAbbreviation(Abbreviation.ANL)
                    .setLanguage(new Locale("english")).setOnlineAccess(false).setMaxAttendeesPresent(15)
                    .setResponsibleForGroup(new String[]{"Karol","Maier"}).setContactInformation("karol.maier@myuni.de");

            Group actual = parser.parseLineToGroup("3,Analysis,Anl,english,no,15,Karol Maier,karol.maier@myuni.de");

            Assertions.assertEquals(expected, actual, "Expected parsed line to group");
        }

        @Test
        @DisplayName("Null input should throw IllegalArgumentException")
        void parseLineToStudent_WithNullInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parseLineToGroup(null)
                    , "Expected IllegalArgumentException by null input");
        }

        @Test
        @DisplayName("By input with not enough data should throw IllegalArgumentException")
        void parseLineToGroup_WithNotEnoughDataInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,no,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by not enough data input");
        }

        @Test
        @DisplayName("By input with missing id should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup(",Analysis,Anl,english,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing id input");
        }

        @Test
        @DisplayName("By input with prohibited id should throw IllegalArgumentException")
        void parseLineToGroup_WithProhibitedIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("three,Analysis,Anl,english,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by prohibited id input");
        }

        @Test
        @DisplayName("By input with missing name should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingNameInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,,Anl,english,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing name input");
        }

        @Test
        @DisplayName("By input with missing abbreviation should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingAbbreviationInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,,english,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing abbreviation input");
        }

        @Test
        @DisplayName("By input with prohibited abbreviation should throw IllegalArgumentException")
        void parseLineToGroup_WithProhibitedAbbreviationInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,emdfe,english,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by prohibited abbreviation input");
        }

        @Test
        @DisplayName("By input with missing language should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingLanguageInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,,no,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing language input");
        }

        @Test
        @DisplayName("By input with missing online access should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingOnlineAccessInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing online access input");
        }

        @Test
        @DisplayName("By input with prohibited online access should throw IllegalArgumentException")
        void parseLineToGroup_WithProhibitedOnlineAccessInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,maybe,15,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by prohibited online access input");
        }

        @Test
        @DisplayName("By input with missing max attendees present should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingMaxAttendeesPresentInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,no,,Karol Maier,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing max attendees present input");
        }

        @Test
        @DisplayName("By input with missing responsible for group should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingResponsibleForGroupInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,no,15,,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by missing responsible for group access input");
        }

        @Test
        @DisplayName("By input with prohibited responsible for group should throw IllegalArgumentException")
        void parseLineToGroup_WithProhibitedResponsibleForGroupInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,no,15,someone,karol.maier@myuni.de")
                    , "Expected IllegalArgumentException by prohibited responsible for group access input");
        }

        @Test
        @DisplayName("By input with missing contact information should throw IllegalArgumentException")
        void parseLineToGroup_WithMissingContactInformationInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");

            Assertions.assertThrows(IllegalArgumentException.class
                    , () -> parser.parseLineToGroup("3,Analysis,Anl,english,no,15,Karol Maier,")
                    , "Expected IllegalArgumentException by missing contact information input");
        }
    }

    @Nested
    @DisplayName("Test parseStudentToLine method")
    class ParseStudentToLineTests{

        @Test
        @DisplayName("Student should be parsed to line")
        void shouldParseStudentToLine(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = "4,Name,Surname,FEMALE,19.5.1999,Citizenship,PlaceOfBirth,STIPEND,1,ONLINE,name.surname@gmail.com";

            String actual = parser.parseStudentToLine(new Student().setId(4).setName("Name").setSurname("Surname")
                    .setGender(Gender.FEMALE).setBirthDate(LocalDate.of(1999,5,19))
                    .setCitizenship("Citizenship").setPlaceOfBirth("PlaceOfBirth").setTypeOfContract(TypeOfContract.STIPEND)
                    .setTypeOfStudying(TypeOfStudying.ONLINE).setGroupId(1).setContactInformation("name.surname@gmail.com"));


            Assertions.assertEquals(expected, actual, "Expected parsed student to line");
        }

        @Test
        @DisplayName("By null input should return empty line")
        void parseStudentToLine_WithNullInput_ShouldReturnEmptyString(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = "";

            String actual = parser.parseStudentToLine(null);

            Assertions.assertEquals(expected, actual, "Expected parsed null to empty line");
        }

        @Test
        @DisplayName("Student with no data should be parsed to line with separators")
        void parseStudentToLine_WithStudentWithNoDataInput_ShouldReturnLine(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = ",,,,,,,,,,";

            String actual = parser.parseStudentToLine(new Student());

            Assertions.assertEquals(expected, actual, "Expected parsed student with no data to line with separators");
        }

    }

    @Nested
    @DisplayName("Test parseGroupToLine method")
    class ParseGroupToLineTests{

        @Test
        @DisplayName("Group should be parsed to line")
        void shouldParseGroupToLine(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = "2,Discrete structures,DS,germany,true,50,Name Surname,name.surname@gmail.com";

            String actual = parser.parseGroupToLine(new Group().setId(2).setName("Discrete structures")
                    .setAbbreviation(Abbreviation.DS).setLanguage(new Locale("germany")).setOnlineAccess(true).setMaxAttendeesPresent(50)
                    .setResponsibleForGroup(new String[]{"Name","Surname"}).setContactInformation("name.surname@gmail.com"));

            Assertions.assertEquals(expected, actual, "Expected parsed group to line");
        }

        @Test
        @DisplayName("Null should be parsed to empty line")
        void parseGroupToLine_WithNullInput_ShouldReturnEmptyLine(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            String expected = "";

            String actual = parser.parseGroupToLine(null);

            Assertions.assertEquals(expected, actual, "Expected parsed null to empty line");
        }

        @Test
        @DisplayName("Group with no data should be parsed to line with separators")
        void parseGroupToLine_WithGroupWithNoDataInput_ShouldReturnLineWithSeparators(){
            SeparatedValuesParser parser = new SeparatedValuesParser("");
            String expected = ",,,,,,,";

            String actual = parser.parseGroupToLine(new Group());

            Assertions.assertEquals(expected, actual, "Expected parsed group with no data to line with separators");
        }
    }

}

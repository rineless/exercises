package util.parser;

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
        @DisplayName("Input with missing birth date should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingBirthDateInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,,Ungarn,Paks,payable,1,online,alicook@gmail.com")
                    , "Expected by input with missing birth date IllegalArgumentException");
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
        @DisplayName("Input with missing group id should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingGroupIdInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,,online,alicook@gmail.com")
                    , "Expected by input with missing group id IllegalArgumentException");
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
        @DisplayName("Input with missing contact information should throw IllegalArgumentException")
        void parseLineToStudent_WithMissingContactInformationInput_ShouldThrowException(){
            SeparatedValuesParser parser = new SeparatedValuesParser(",");
            Assertions.assertThrows(IllegalArgumentException.class
                    , ()->parser.parseLineToStudent("3,Alice,Cook,f,14.04.1999,Ungarn,Paks,payable,1,online,")
                    , "Expected by input with missing contact information IllegalArgumentException");
        }


    }


}

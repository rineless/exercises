package additional;

import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;

import java.time.LocalDate;


public class StudentGroupSource {
    public static final String existingStudentID1 = "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com";
    public static final String existingStudentID2 = "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";
    public static final String existingStudentID3 = "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com";
    public static final String notExistingStudent = "4,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";
    public static final String updatedStudentID2 = "2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";

    public static Student existingStudentID1(){
        return new Student().setId(1).setName("Anna").setSurname("Allen").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1998, 11, 7)).setCitizenship("German").setPlaceOfBirth("Hamburg")
                .setTypeOfContract(TypeOfContract.STIPEND).setGroupId(1).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("anna.allen98@gmail.com");
    }

    public static Student existingStudentID2(){
        return notValidStudentID2().setName("Peter").setGroupId(2);
    }

    public static Student updatedStudentID2(){
        return existingStudentID2().setName("Bob");
    }

    public static Student notValidStudentID2(){
        return new Student().setId(2).setSurname("Tailor").setGender(Gender.MALE)
                .setBirthDate(LocalDate.of(1999, 6, 25)).setCitizenship("German").setPlaceOfBirth("Bremen")
                .setTypeOfContract(TypeOfContract.PAYABLE).setTypeOfStudying(TypeOfStudying.PRESENT)
                .setContactInformation("petter99tailor@gmail.com");
    }

    public static Student existingStudentID3(){
        return new Student().setId(3).setName("Alice").setSurname("Cook").setGender(Gender.FEMALE)
                .setBirthDate(LocalDate.of(1999, 4, 14)).setCitizenship("Ungarn").setPlaceOfBirth("Paks")
                .setTypeOfContract(TypeOfContract.PAYABLE).setGroupId(1).setTypeOfStudying(TypeOfStudying.ONLINE)
                .setContactInformation("alicook@gmail.com");
    }

    public static Student notExistingStudentID4(){
        return existingStudentID2().setId(4);
    }

    public static Student notValidStudentID4(){
        return notValidStudentID2().setId(4);
    }

}

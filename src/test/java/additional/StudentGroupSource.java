package additional;

import model.group.Group;
import model.group.GroupNames;
import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;

import java.time.LocalDate;
import java.util.Locale;


public class StudentGroupSource {
    public static final String existingStudentID1 = "1,Anna,Allen,FEMALE,7.11.1998,German,Hamburg,STIPEND,1,PRESENT,anna.allen98@gmail.com";
    public static final String existingStudentID2 = "2,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";
    public static final String existingStudentID3 = "3,Alice,Cook,FEMALE,14.4.1999,Ungarn,Paks,PAYABLE,1,ONLINE,alicook@gmail.com";
    public static final String notExistingStudent = "4,Peter,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";
    public static final String updatedStudentID2 = "2,Bob,Tailor,MALE,25.6.1999,German,Bremen,PAYABLE,2,PRESENT,petter99tailor@gmail.com";


    public static final String existingGroupID1 = "1,DS,germany,true,30,Adam Becker,adam.becker@myuni.de";
    public static final String existingGroupID2 = "2,ALG,english,true,20,Shon Braun,shon.braun@myuni.de";
    public static final String existingGroupID3 = "3,ANL,english,false,15,Karol Maier,karol.maier@myuni.de";
    public static final String notExistingGroup = "4,ALG,english,true,20,Shon Braun,shon.braun@myuni.de";
    public static final String updatedGroupID2 = "2,ALG,german,true,20,Shon Braun,shon.braun@myuni.de";

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


    public static Group existingGroupID1(){
        return new Group().setId(1).setGroupName(GroupNames.DS).setLanguage(new Locale("germany"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(30)
                .setResponsibleForGroup(new String[]{"Adam","Becker"}).setContactInformation("adam.becker@myuni.de");
    }

    public static Group existingGroupID2(){
        return new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("english"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");
    }

    public static Group existingGroupID3(){
        return new Group().setId(3).setGroupName(GroupNames.ANL).setLanguage(new Locale("english"))
                .setMaxAttendeesPresent(15)
                .setResponsibleForGroup(new String[]{"Karol","Maier"}).setContactInformation("karol.maier@myuni.de");

    }

    public static Group notExistingGroup(){
        return new Group().setId(4).setGroupName(GroupNames.ALG).setLanguage(new Locale("english"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");
    }

    public static Group notValidGroupID2(){
        return new Group().setId(2).setLanguage(new Locale("english"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");
    }

    public static Group notValidGroupID4(){
        return new Group().setId(4).setLanguage(new Locale("english"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");
    }

    public static Group updatedGroupID2(){
        return new Group().setId(2).setGroupName(GroupNames.ALG).setLanguage(new Locale("german"))
                .isOnlineAccessible(true).setMaxAttendeesPresent(20)
                .setResponsibleForGroup(new String[]{"Shon","Braun"}).setContactInformation("shon.braun@myuni.de");
    }

}

package model;

import java.util.Date;

public class Student {
    private final int id;
    private final String name;
    private final String surname;
    private final String gender;
    private final Date birthDate;
    private final String citizenship;
    private final String placeOfBirth;
    private final String typeOfContract;
    private final int groupId;
    private final String typeOfStudying;
    private final String contractInformation;

    public Student(int id, String name, String surname, String gender, String birthDate
            , String citizenship, String placeOfBirth, String typeOfContract
            , int groupId, String typeOfStudying, String contractInformation){
      this.id = id;
      this.name = name;
      this.surname = surname;
      this.gender = gender;
      //this.birthDate = birthDate; TODO
      this.citizenship = citizenship;
      this.placeOfBirth = placeOfBirth;
      this.typeOfContract = typeOfContract;
      this.groupId = groupId;
      this.typeOfStudying = typeOfStudying;
      this.contractInformation = contractInformation;
    }

}

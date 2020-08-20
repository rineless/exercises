package model.student;

import util.SeparatedValuesParser;

import java.time.LocalDate;

public class Student {

    private final int id;
    private final String name;
    private final String surname;
    private final String gender;
    private final LocalDate birthDate;
    private final String citizenship;
    private final String placeOfBirth;
    private final String typeOfContract;
    private final int groupId;
    private final String typeOfStudying;
    private final String contractInformation;

    //TODO check if arguments valid
    public Student(int id, String name, String surname, String gender, String birthDate
            , String citizenship, String placeOfBirth, String typeOfContract
            , int groupId, String typeOfStudying, String contractInformation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        String[] date = new SeparatedValuesParser(".").parseLineToArray(birthDate);
        this.birthDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
        this.citizenship = citizenship;
        this.placeOfBirth = placeOfBirth;
        this.typeOfContract = typeOfContract;
        this.groupId = groupId;
        this.typeOfStudying = typeOfStudying;
        this.contractInformation = contractInformation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getTypeOfContract() {
        return typeOfContract;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getTypeOfStudying() {
        return typeOfStudying;
    }

    public String getContractInformation() {
        return contractInformation;
    }

}
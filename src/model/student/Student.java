package model.student;

import util.SeparatedValuesParser;

import java.time.LocalDate;

public class Student {

    private int id;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String citizenship;
    private String placeOfBirth;
    private TypeOfContract typeOfContract;
    private int groupId;
    private TypeOfStudying typeOfStudying;
    private String contractInformation;

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

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Student setGender(String gender) {
        if(gender.contentEquals("f"))
            this.gender = Gender.valueOf("female");
        else
            this.gender = Gender.valueOf("male");
        return this;
    }

    public Student setBirthDate(String birthDate) {
        String[] date = new SeparatedValuesParser(".").parseLineToArray(birthDate);
        this.birthDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1])
                , Integer.parseInt(date[0]));
        return this;
    }

    public Student setCitizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public Student setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public Student setTypeOfContract(String typeOfContract) {
        this.typeOfContract = TypeOfContract.valueOf(typeOfContract);
        return this;
    }

    public Student setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public Student setTypeOfStudying(String typeOfStudying) {
        this.typeOfStudying = TypeOfStudying.valueOf(typeOfStudying);
        return this;
    }

    public Student setContractInformation(String contractInformation) {
        this.contractInformation = contractInformation;
        return this;
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

    public Gender getGender() {
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

    public TypeOfContract getTypeOfContract() {
        return typeOfContract;
    }

    public int getGroupId() {
        return groupId;
    }

    public TypeOfStudying getTypeOfStudying() {
        return typeOfStudying;
    }

    public String getContractInformation() {
        return contractInformation;
    }

}

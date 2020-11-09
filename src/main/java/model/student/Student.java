package model.student;

import util.parser.SeparatedValuesParser;

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
    private String contactInformation;

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public Student setName(String name) throws IllegalArgumentException{
        if (name == null)
            throw new IllegalArgumentException("Null cannot be resolved into student name");
        if (name.contentEquals(""))
            throw new IllegalArgumentException("Empty student name is prohibited");

        this.name = name;
        return this;
    }

    public Student setSurname(String surname) throws IllegalArgumentException{
        if (surname == null)
            throw new IllegalArgumentException("Null cannot be resolved to student surname");
        if (surname.contentEquals(""))
            throw new IllegalArgumentException("Empty student surname is prohibited");

        this.surname = surname;
        return this;
    }

    public Student setGender(String gender) throws IllegalArgumentException{
        if (gender.toLowerCase().contentEquals("f") | gender.toUpperCase().contentEquals("FEMALE"))
            this.gender = Gender.valueOf("FEMALE");
        else if (gender.toLowerCase().contentEquals("m") | gender.toUpperCase().contentEquals("MALE"))
            this.gender = Gender.valueOf("MALE");
        else
            throw new IllegalArgumentException("Incorrect input of gender for student (should be one of m|M|f|F)");
        return this;
    }

    public Student setGender(Gender gender) throws IllegalArgumentException{
        if(gender == null)
            throw new IllegalArgumentException("Null cannot be resolved to student gender");

        this.gender = gender;
        return this;
    }

    public Student setBirthDate(String birthDate) {
        String[] date = new SeparatedValuesParser("\\.").parseLineToArray(birthDate);
        this.birthDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1])
                , Integer.parseInt(date[0]));
        return this;
    }

    public Student setBirthDate(LocalDate birthDate) throws IllegalArgumentException{
        if(birthDate == null)
            throw new IllegalArgumentException("Null cannot be resolved to student birth date");

        this.birthDate = birthDate;
        return this;
    }

    public Student setCitizenship(String citizenship) throws IllegalArgumentException{
        if (citizenship == null)
            throw new IllegalArgumentException("Null cannot be resolved to student citizenship");
        if (citizenship.contentEquals(""))
            throw new IllegalArgumentException("Empty student citizenship is prohibited");

        this.citizenship = citizenship;
        return this;
    }

    public Student setPlaceOfBirth(String placeOfBirth) throws IllegalArgumentException{
        if (placeOfBirth == null)
            throw new IllegalArgumentException("Null cannot be resolved to student placeOfBirth");
        if (placeOfBirth.contentEquals(""))
            throw new IllegalArgumentException("Empty student placeOfBirth is prohibited");

        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public Student setTypeOfContract(String typeOfContract) throws IllegalArgumentException{
        this.typeOfContract = TypeOfContract.valueOf(typeOfContract.toUpperCase());
        return this;
    }

    public Student setTypeOfContract(TypeOfContract typeOfContract) throws IllegalArgumentException{
        if(typeOfContract == null)
            throw new IllegalArgumentException("Null cannot be resolved to student type of contract");

        this.typeOfContract = typeOfContract;
        return this;
    }

    public Student setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public Student setTypeOfStudying(String typeOfStudying) throws IllegalArgumentException{
        this.typeOfStudying = TypeOfStudying.valueOf(typeOfStudying.toUpperCase());
        return this;
    }

    public Student setTypeOfStudying(TypeOfStudying typeOfStudying) throws IllegalArgumentException{
        if(typeOfStudying == null)
            throw new IllegalArgumentException("Null cannot be resolved to student type of studying");

        this.typeOfStudying = typeOfStudying;
        return this;
    }

    public Student setContactInformation(String contactInformation) throws IllegalArgumentException{
        if (contactInformation == null)
            throw new IllegalArgumentException("Null cannot be resolved to student contract information");

        this.contactInformation = contactInformation;
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

    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public String toString(){
        return new SeparatedValuesParser(", ").parseStudentToLine(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj instanceof Student){
            return equals((Student) obj);
        }

        return false;
    }

    public boolean equals(Student student){
        if(student == null)
            return false;
        try {
            if (this.id == student.id && this.name.contentEquals(student.name)
                    && this.surname.contentEquals(student.surname) && this.gender == student.getGender()
                    && this.birthDate.equals(student.getBirthDate()) && this.citizenship.contentEquals(student.getCitizenship())
                    && this.placeOfBirth.contentEquals(student.getPlaceOfBirth())
                    && this.typeOfContract == student.getTypeOfContract() && this.groupId == student.getGroupId()
                    && this.typeOfStudying == student.getTypeOfStudying() && this.contactInformation.contentEquals(student.getContactInformation()))
                return true;
        }
        catch(NullPointerException ex){
            return false;
        }
        return false;
    }

}
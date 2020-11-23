package model.student;

import util.parser.SeparatedValuesParser;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg"
            , Locale.getDefault());

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public Student setName(String name) throws IllegalArgumentException{
        if (name == null)
            throw new IllegalArgumentException(properties.getString("student.null_name"));

        this.name = name;
        return this;
    }

    public Student setSurname(String surname) throws IllegalArgumentException{
        if (surname == null)
            throw new IllegalArgumentException(properties.getString("student.null_surname"));

        this.surname = surname;
        return this;
    }

    public Student setGender(String gender) throws IllegalArgumentException{
        if(gender == null)
            throw new IllegalArgumentException(properties.getString("student.null_gender"));

        if(gender.toUpperCase().matches(properties.getString("student.gender_regex"))) {
            List<String>  genders = Stream.of("MALE|FEMALE".split("\\|")).collect(Collectors.toList());
            if(genders.indexOf(gender) == 0)
                this.gender = Gender.valueOf("MALE");
            else
                this.gender = Gender.valueOf("FEMALE");
        }
        else
            throw new IllegalArgumentException(properties.getString("student.incorrect_gender"));
        return this;
    }

    public Student setGender(Gender gender) throws IllegalArgumentException{
        if(gender == null)
            throw new IllegalArgumentException(properties.getString("student.null_gender"));

        this.gender = gender;
        return this;
    }

    public Student setBirthDate(String birthDate) {
        if(birthDate == null)
            throw new IllegalArgumentException(properties.getString("student.null_birthDate"));

        if(birthDate.matches("[0-9]+\\\\.[0-9]+\\\\.[0-9]+")) {
            String[] date = birthDate.split("\\.");
            this.birthDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1])
                    , Integer.parseInt(date[0]));
        }
        else
            throw new IllegalArgumentException(properties.getString("student.incorrect_birthDate"));

        return this;
    }

    public Student setBirthDate(LocalDate birthDate) throws IllegalArgumentException{
        if(birthDate == null)
            throw new IllegalArgumentException(properties.getString("student.null_birthDate"));

        this.birthDate = birthDate;
        return this;
    }

    public Student setCitizenship(String citizenship) throws IllegalArgumentException{
        if (citizenship == null)
            throw new IllegalArgumentException(properties.getString("student.null_citizenship"));

        this.citizenship = citizenship;
        return this;
    }

    public Student setPlaceOfBirth(String placeOfBirth) throws IllegalArgumentException{
        if (placeOfBirth == null)
            throw new IllegalArgumentException(properties.getString("student.null_placeOfBirth"));

        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public Student setTypeOfContract(String typeOfContract) throws IllegalArgumentException{
        if(typeOfContract == null)
            throw new IllegalArgumentException(properties.getString("student.null_typeOfContract"));

        if(typeOfContract.toUpperCase().matches("STIPEND|PAYABLE"))
            this.typeOfContract = TypeOfContract.valueOf(typeOfContract.toUpperCase());
        else
            throw new IllegalArgumentException(properties.getString("student.incorrect_typeOfContract"));
        return this;
    }

    public Student setTypeOfContract(TypeOfContract typeOfContract) throws IllegalArgumentException{
        if(typeOfContract == null)
            throw new IllegalArgumentException(properties.getString("student.null_typeOfContract"));

        this.typeOfContract = typeOfContract;
        return this;
    }

    public Student setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public Student setTypeOfStudying(String typeOfStudying) throws IllegalArgumentException{
        if(typeOfStudying == null)
            throw new IllegalArgumentException(properties.getString("student.null_typeOfStudying"));

        if(typeOfStudying.toUpperCase().matches("ONLINE|PRESENT"))
            this.typeOfStudying = TypeOfStudying.valueOf(typeOfStudying.toUpperCase());
        else
            throw new IllegalArgumentException(properties.getString("student.incorrect_typeOfStudying"));
        return this;
    }

    public Student setTypeOfStudying(TypeOfStudying typeOfStudying) throws IllegalArgumentException{
        if(typeOfStudying == null)
            throw new IllegalArgumentException(properties.getString("student.null_typeOfStudying"));

        this.typeOfStudying = typeOfStudying;
        return this;
    }

    public Student setContactInformation(String contactInformation) throws IllegalArgumentException{
        if (contactInformation == null)
            throw new IllegalArgumentException(properties.getString("student.null_contactInf"));

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

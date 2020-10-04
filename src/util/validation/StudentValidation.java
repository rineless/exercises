package util.validation;

import model.student.Student;

public class StudentValidation implements Validation{

    @Override
    public boolean isValid(Object student) {
        if (student != null) {
            if (student instanceof Student) {
                return isValid((Student) student);
            }
        }
        return false;
    }

    public boolean isValid(Student student) {
        if (student != null) {
            if (student.getName() != null & student.getSurname() != null & student.getGender() != null
                    & student.getBirthDate() != null & student.getCitizenship() != null & student.getPlaceOfBirth() != null
                    & student.getTypeOfContract() != null & student.getTypeOfStudying() != null & student.getContactInformation() != null)
                return true;
        }
        return false;
    }
}

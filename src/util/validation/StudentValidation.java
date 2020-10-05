package util.validation;

import model.student.Student;

import java.util.Objects;

public class StudentValidation implements Validation{

    @Override
    public boolean isValid(Object student) {
        if (Objects.nonNull(student)) {
            if (student instanceof Student) {
                return isValid((Student) student);
            }
        }
        return false;
    }

    public static boolean isValid(Student student) {
        if (Objects.nonNull(student)) {
            if (Objects.nonNull(student.getName()) & Objects.nonNull(student.getSurname()) & Objects.nonNull(student.getGender())
                    & Objects.nonNull(student.getBirthDate()) & Objects.nonNull(student.getCitizenship()) & Objects.nonNull(student.getPlaceOfBirth())
                    & Objects.nonNull(student.getTypeOfContract()) & Objects.nonNull(student.getTypeOfStudying()) & Objects.nonNull(student.getContactInformation()))
                return true;
        }
        return false;
    }
}

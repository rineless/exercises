package service;

import model.group.Group;
import model.student.Gender;
import model.student.Student;
import model.student.TypeOfContract;
import model.student.TypeOfStudying;
import repository.CSVGroupsRepository;
import repository.CSVStudentsRepository;
import repository.IGroupsRepository;
import repository.IStudentsRepository;
import util.validation.StudentValidation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentsService implements IStudentsService{
    private final IStudentsRepository studentsRepository;
    private final IGroupsRepository groupsRepository;

    public StudentsService(){
        studentsRepository = new CSVStudentsRepository();
        groupsRepository = new CSVGroupsRepository();
    }

    public List<Student> getAll(){
        return studentsRepository.getAll();
    }

    public Student getById(int id){
        return studentsRepository.getById(id);
    }

    public void add(Student student) {

        if (StudentValidation.isValid(student)) {
            if (student.getTypeOfStudying() == TypeOfStudying.PRESENT) {

                if (!studentExists(student.getId())) {
                    if (studentIsAddibleToGroup(student))
                        studentsRepository.add(student);
                }

            } else
                studentsRepository.add(student);
        }
    }

    public void update(Student student) {

        if (StudentValidation.isValid(student)) {
            if (student.getTypeOfStudying() == TypeOfStudying.PRESENT) {

                Student studentToUpdate = studentsRepository.getById(student.getId());
                if (studentToUpdate != null) {
                    if (studentToUpdate.getGroupId() != student.getGroupId()) {

                        if (studentIsAddibleToGroup(student))
                            studentsRepository.update(student);

                    } else
                        studentsRepository.update(student);
                }

            } else
                studentsRepository.update(student);
        }
    }

    private boolean studentIsAddibleToGroup(Student student) {

        Group group = groupsRepository.getById(student.getGroupId());
        if (group != null) {

            int numberOfStudentsInGroup = getAttendeesStudentsInGroup(group.getId()).size();

            if (numberOfStudentsInGroup < group.getMaxAttendeesPresent())
                return true;
            else {
                System.out.println(group.getId() + "Group is full. Student cannot be added");
                return false;
            }

        } else {
            System.out.println("Student attends not existing group. Cannot be added to repository");
            return false;
        }
    }

    public void delete(Student student){
        studentsRepository.delete(student);
    }

    public boolean studentExists(int id){
        return Objects.nonNull(getById(id));
    }

    public List<Student> getAllStudentsInGroup(int id){
        return getAll().stream().filter(student -> student.getGroupId() == id)
                .collect(Collectors.toList());
    }

    public List<Student> getAttendeesStudentsInGroup(int id){
        return getAll().stream().filter(student -> student.getGroupId() == id
                && student.getTypeOfStudying() == TypeOfStudying.PRESENT).collect(Collectors.toList());
    }

    public List<Student> getOnlineStudentsInGroup(int id){
        return getAll().stream().filter(student -> student.getGroupId() == id
                && student.getTypeOfStudying() == TypeOfStudying.ONLINE).collect(Collectors.toList());
    }

    public List<Student> findStipends(){
        return getAll().stream().filter(student -> student.getTypeOfContract() == TypeOfContract.STIPEND)
                .collect(Collectors.toList());
    }

    public List<Student> findStudentsWithPayableContract(){
        return getAll().stream().filter(student -> student.getTypeOfContract() == TypeOfContract.PAYABLE)
                .collect(Collectors.toList());
    }

    public List<Student> findAttendeesStudents(){
        return getAll().stream().filter(student -> student.getTypeOfStudying() == TypeOfStudying.PRESENT)
                .collect(Collectors.toList());
    }

    public List<Student> findOnlineStudents(){
        return getAll().stream().filter(student -> student.getTypeOfStudying() == TypeOfStudying.ONLINE)
                .collect(Collectors.toList());
    }

    public List<Student> findAllMaleStudents(){
        return getAll().stream().filter(student -> student.getGender() == Gender.MALE)
                .collect(Collectors.toList());
    }

    public List<Student> findAllFemaleStudents(){
        return getAll().stream().filter(student -> student.getGender() == Gender.FEMALE)
                .collect(Collectors.toList());
    }
}

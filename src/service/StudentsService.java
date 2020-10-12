package service;

import model.group.Group;
import model.student.Student;
import model.student.TypeOfStudying;
import repository.CSVGroupsRepository;
import repository.CSVStudentsRepository;
import repository.IGroupsRepository;
import repository.IStudentsRepository;
import util.validation.StudentValidation;

import java.util.List;
import java.util.Optional;
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

                if (studentIsAddibleToGroup(student))
                    studentsRepository.add(student);

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

    public List<Student> getAllStudentsInGroup(int id){
        return getAll().stream().filter(studentInRepository -> studentInRepository.getGroupId() == id)
                .collect(Collectors.toList());
    }

    public List<Student> getAttendeesStudentsInGroup(int id){
        //TODO
        return null;
    }

    public List<Student> getOnlineStudentsInGroup(int id){
        //TODO
        return null;
    }

    public List<Student> findStipends(){
        //TODO
        return null;
    }

    public List<Student> findStudentsWithPayableContract(){
        //TODO
        return null;
    }

    public List<Student> findAttendeesStudents(){
        //TODO
        return null;
    }

    public List<Student> findOnlineStudents(){
        //TODO
        return null;
    }

    public List<Student> findAllMaleStudents(){
        //TODO
        return null;
    }

    public List<Student> findAllFemaleStudents(){
        //TODO
        return null;
    }
}

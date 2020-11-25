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
import util.reader.FileReader;
import util.validation.StudentValidation;
import util.writer.FileWriter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentsService implements IStudentsService{
    private final IStudentsRepository studentsRepository;
    private final IGroupsRepository groupsRepository;

    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg"
            , Locale.getDefault());

    public StudentsService(){
        studentsRepository = new CSVStudentsRepository(new FileReader(), new FileWriter());
        groupsRepository = new CSVGroupsRepository();
    }

    public List<Student> getAll(){
        return studentsRepository.getAll();
    }

    public Student getById(int id) {
        return studentsRepository.getById(id);
    }

    public void add(Student student) {

        if (StudentValidation.isValid(student)) {
            if (student.getTypeOfStudying() == TypeOfStudying.PRESENT) {

                if (!studentExists(student.getId())) {
                    if (studentIsAddableToGroup(student))
                        studentsRepository.add(student);
                }

            } else {
                Group group = groupsRepository.getById(student.getGroupId());
                if (Objects.nonNull(group)) {
                    if (group.isOnlineAccessible())
                        studentsRepository.add(student);
                    else
                        System.out.println(group.getId() + " " + properties.getString("studentsService.not_online_accessible")
                                + properties.getString("studentsService.adding_interrupted"));
                } else
                    System.out.println(student.getGroupId() + " " + properties.getString("studentsService.group_not_exist")
                            + properties.getString("studentsService.adding_interrupted"));
            }
        }
    }

    public void update(Student student) {

        if (StudentValidation.isValid(student)) {
            if (student.getTypeOfStudying() == TypeOfStudying.PRESENT) {

                Student studentToUpdate = studentsRepository.getById(student.getId());
                if (studentToUpdate != null) {
                    if (studentToUpdate.getGroupId() != student.getGroupId()
                            || studentToUpdate.getTypeOfStudying() == TypeOfStudying.ONLINE) {

                        if (studentIsAddableToGroup(student))
                            studentsRepository.update(student);

                    } else
                        studentsRepository.update(student);
                }

            } else {
                Group group = groupsRepository.getById(student.getGroupId());
                if (Objects.nonNull(group)) {
                    if (group.isOnlineAccessible())
                        studentsRepository.update(student);
                    else
                        System.out.println(group.getId() + " " + properties.getString("studentsService.not_online_accessible")
                                + properties.getString("studentsService.updating_interrupted"));
                } else
                    System.out.println(student.getGroupId() + " " + properties.getString("studentsService.group_not_exist")
                            + properties.getString("studentsService.updating_interrupted"));
            }
        }
    }

    private boolean studentIsAddableToGroup(Student student) {

        Group group = groupsRepository.getById(student.getGroupId());
        if (group != null) {

            int numberOfStudentsInGroup = getAttendeesStudentsInGroup(group.getId()).size();

            if (numberOfStudentsInGroup < group.getMaxAttendeesPresent())
                return true;
            else {
                System.out.println(group.getId() + " " + properties.getString("studentsService.group_is_full")
                        + properties.getString("studentsService.cannot_added"));
                return false;
            }

        } else {
            System.out.println(properties.getString("studentsService.not_existing_group")
                    + properties.getString("studentsService.cannot_added"));
            return false;
        }
    }

    public void delete(Student student){
        if(StudentValidation.isValid(student)) {
            if(studentExists(student.getId()))
                studentsRepository.delete(student);
            else
                System.out.println(properties.getString("studentsService.not_exist")
                        + properties.getString("studentsService.cannot_delete"));
        }
        else
            System.out.println(properties.getString("studentsService.not_exist")
                    + properties.getString("studentsService.cannot_delete"));
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

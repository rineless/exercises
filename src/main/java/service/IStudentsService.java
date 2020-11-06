package service;

import model.group.Group;
import model.student.Student;

import java.util.List;

public interface IStudentsService {
    List<Student> getAll();

    Student getById(int id);

    void add(Student student);

    void update(Student student);

    void delete(Student student);

    List<Student> getAllStudentsInGroup(int id);

    List<Student> getAttendeesStudentsInGroup(int id);

    List<Student> getOnlineStudentsInGroup(int id);

    List<Student> findStipends();

    List<Student> findStudentsWithPayableContract();

    List<Student> findAttendeesStudents();

    List<Student> findOnlineStudents();

    List<Student> findAllMaleStudents();

    List<Student> findAllFemaleStudents();
}

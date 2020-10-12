package repository;

import model.student.Student;

import java.util.List;

public interface IStudentsRepository {

    List<Student> getAll();

    Student getById(int id);

    void add(Student student);

    void update(Student student);

    void delete(Student student);
}

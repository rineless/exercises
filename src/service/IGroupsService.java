package service;

import model.group.Group;
import model.student.Student;

import java.util.List;

public interface IGroupsService {
    List<Student> findAllStudentsInGroup(int id);
    List<Group> findFullGroups();
}

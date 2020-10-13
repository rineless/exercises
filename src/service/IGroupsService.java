package service;

import model.group.Group;
import model.group.GroupNames;
import model.student.Student;

import java.util.List;
import java.util.Locale;

public interface IGroupsService {

    List<Group> getAll();

    Group getById(int id);

    void add(Group group);

    void update(Group group);

    void delete(Group group);

    List<Student> findAllStudentsInGroup(int id);

    List<Group> findFullGroups();

    List<Group> findAllGroupsByLanguage(Locale language);

    List<Group> findAllGroupsWithOnlineAccess(boolean onlineAccess);

    List<Group> findAllGroupsByName(GroupNames groupName);
}

package repository;

import model.group.Group;

import java.util.List;

public interface GroupsRepository {
    List<Group> getAll();

    Group getById(int id);

    void add(Group group);

    void update(Group group);

    void delete(Group group);
}

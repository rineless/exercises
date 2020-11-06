package service;

import model.group.Group;
import model.group.GroupNames;
import model.student.Student;
import repository.CSVGroupsRepository;
import repository.CSVStudentsRepository;
import repository.IGroupsRepository;
import repository.IStudentsRepository;
import util.validation.GroupValidation;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupsService implements IGroupsService{
    private final IStudentsRepository studentsRepository;
    private final IGroupsRepository groupsRepository;

    public GroupsService() {
        studentsRepository = new CSVStudentsRepository();
        groupsRepository = new CSVGroupsRepository();
    }

    public List<Group> getAll() {
        return groupsRepository.getAll();
    }

    public Group getById(int id) {
        return groupsRepository.getById(id);
    }

    public void add(Group group) {
        if (GroupValidation.isValid(group)) {
            if (Objects.isNull(getById(group.getId())))
                groupsRepository.add(group);
            else
                System.out.println("Group already exists");
        }
    }

    public void update(Group group){
        groupsRepository.update(group);
    }

    public void delete(Group group){
        if(GroupValidation.isValid(group)) {
            if(Objects.nonNull(groupsRepository.getById(group.getId())))
                groupsRepository.delete(group);
            else
                System.out.println("Group do not exist. Cannot be deleted");
        }
        else
            System.out.println("Group do not exist. Cannot be deleted");
    }

    public List<Student> findAllStudentsInGroup(int id){
        return studentsRepository.getAll().stream().filter(student -> student.getId() == id)
                .collect(Collectors.toList());
    }

    public List<Group> findFullGroups(){
        List<Group> fullGroups = new LinkedList<>();
        List<Group> groups = getAll();
        for(Group group: groups){
            long studentNumber = findAllStudentsInGroup(group.getId()).size();
            if (studentNumber == group.getMaxAttendeesPresent())
                fullGroups.add(group);
        }
        return fullGroups;
    }

    public List<Group> findAllGroupsByLanguage(Locale language){
        return getAll().stream().filter(group -> group.getLanguage().equals(language)).collect(Collectors.toList());
    }

    public List<Group> findAllGroupsWithOnlineAccess(boolean onlineAccess){
        return getAll().stream().filter(group -> group.isOnlineAccessible()).collect(Collectors.toList());
    }

    public List<Group> findAllGroupsByName(GroupNames groupName){
        return getAll().stream().filter(group -> group.getGroupName() == groupName).collect(Collectors.toList());
    }
}

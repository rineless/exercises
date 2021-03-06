package service;

import model.group.Group;
import model.group.GroupNames;
import model.student.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CSVGroupsRepository;
import repository.CSVStudentsRepository;
import repository.IGroupsRepository;
import repository.IStudentsRepository;
import util.parser.CSVParser;
import util.reader.FileReader;
import util.validation.GroupValidation;
import util.writer.FileWriter;

import java.util.*;
import java.util.stream.Collectors;

public class GroupsService implements IGroupsService{
    private final Logger logger = LogManager.getLogger(GroupsService.class);

    private final IStudentsRepository studentsRepository;
    private final IGroupsRepository groupsRepository;

    private final ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg"
            , Locale.getDefault());

    public GroupsService() {
        studentsRepository = new CSVStudentsRepository(new FileReader(), new FileWriter(), new CSVParser());
        groupsRepository = new CSVGroupsRepository(new FileReader(), new FileWriter(), new CSVParser());
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
                logger.warn(properties.getString("groupsService.already_exist"));
        }
        else
            logger.warn(properties.getString("groupsService.not_valid"));
    }

    public void update(Group group){
        groupsRepository.update(group);
    }

    public void delete(Group group){
        if(GroupValidation.isValid(group)) {
            if(Objects.nonNull(groupsRepository.getById(group.getId())))
                groupsRepository.delete(group);
            else
                logger.warn(properties.getString("groupsService.dont_exist")
                        + properties.getString("groupsService.cannot_delete"));
        }
        else
            logger.warn(properties.getString("groupsService.dont_exist")
                    + properties.getString("groupsService.cannot_delete"));
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

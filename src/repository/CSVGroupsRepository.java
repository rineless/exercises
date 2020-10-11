package repository;

import model.group.Group;
import util.finder.PathFinder;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.validation.GroupValidation;
import util.writer.FileWriter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CSVGroupsRepository implements GroupsRepository {
    private final FileReader reader;
    private final FileWriter writer;
    private final ILineParser parser;
    private final String groupsDataPath = "data/StudentGroupData.csv";

    public CSVGroupsRepository() {
        reader = new FileReader();
        writer = new FileWriter();
        parser = new CSVParser();
    }

    public List<Group> getAll() {
        return reader.receiveLinesAsList(PathFinder.findFromResources(groupsDataPath))
                .stream().skip(1).map(line -> parser.parseLineToGroup(line)).collect(Collectors.toList());
    }

    public Group getById(int id) {
        Group group = null;
        Optional<Group> optional = getAll().stream().filter(groupFromList -> groupFromList.getId() == id).findFirst();
        if (optional.isPresent())
            group = optional.get();
        else
            System.out.println("Group not found");
        return group;
    }

    public void add(Group group) {
        if (Objects.nonNull(group)) {
            if (GroupValidation.isValid(group)) {
                writer.appendLine(parser.parseGroupToLine(group) + "\n", PathFinder.findFromResources(groupsDataPath));
            }
        }

    }

    public void update(Group group) {
        if (Objects.nonNull(group)) {
            if (GroupValidation.isValid(group)) {
                List<Group> groupList = getAll();
                Optional<Group> optional = groupList.stream().filter(groupFromList -> groupFromList.getId() == group.getId())
                        .findFirst();
                if (optional.isPresent()) {
                    int lineNumber = groupList.indexOf(optional.get()) + 1;
                    writer.rewriteLine(parser.parseGroupToLine(group), lineNumber, PathFinder.findFromResources(groupsDataPath));
                }
            }
        }
    }


    public void delete(Group group) {
        if (Objects.nonNull(group)) {
            writer.deleteLine(parser.parseGroupToLine(group), PathFinder.findFromResources(groupsDataPath));
        }
    }
}

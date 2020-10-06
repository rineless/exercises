package repository;

import model.group.Group;
import util.finder.PathFinder;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.writer.FileWriter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CSVGroupsRepository implements GroupsRepository {
    private final FileReader reader;
    private final FileWriter writer;
    private final ILineParser parser;
    private final String groupsDataPath = "data/StudentGroupData.csv";

    public CSVGroupsRepository(){
        reader = new FileReader();
        writer = new FileWriter();
        parser = new CSVParser();
    }

    public List<Group> getAll(){
        return reader.receiveLinesAsList(PathFinder.findFromResources(groupsDataPath))
                .stream().map(line -> parser.parseLineToGroup(line)).collect(Collectors.toList());
    };

    public Group getById(int id){
        Group group = null;
        Optional<Group> optional = getAll().stream().filter(groupFromList -> groupFromList.getId() == id).findFirst();
        if(optional.isPresent())
            group = optional.get();
        else
            System.out.println("Group not found");
        return group;
    };

    public void add(Group group){

    };

    public void update(Group group){

    };

    public void delete(Group group){

    };
}

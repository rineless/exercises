package repository;

import model.group.Group;
import util.parser.CSVParser;
import util.parser.ILineParser;
import util.reader.FileReader;
import util.writer.FileWriter;

import java.util.List;

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
        return null;
    };

    public Group getById(int id){
        return null;
    };

    public void add(Group group){

    };

    public void update(Group group){

    };

    public void delete(Group group){

    };
}

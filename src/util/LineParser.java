package util;

public interface LineParser {
    String[] parseLineToArray(String line);
    //TODO
    default Student parseLineToStudent(String line){
        String[] dataForStudent = parseLineToArray(line);
        return new Student();
    };
    //TODO
    default Group parseLineToGroup(String line){
        String[] dataForGroup = parseLineToArray(line);
        return new Group();
    };

}

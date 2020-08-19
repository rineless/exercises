public interface LineParser {
    String[] parseLineToArray(String line);
    //TODO
    default Student parseLineToStudent(String line){
        String[] dataForStudent = parseLineToArray(line);
        return new Student();
    };

}

package menu;

import model.group.Group;
import model.student.Student;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleUserCommunicator implements IUserCommunicator{
    private ConsoleReader reader;
    private ConsoleWriter writer;
    private StudentsService studentsService;
    private GroupsService groupsService;
    private CSVParser parser;

    public ConsoleUserCommunicator(){
        reader = new ConsoleReader();
        writer = new ConsoleWriter();
        studentsService = new StudentsService();
        groupsService = new GroupsService();
        parser = new CSVParser();
    }

    @Override
    public void applyUserCommunicationLogic() {
        System.out.println("Write one of the options");

    }

    public void printOptions(){
        System.out.println("1 : get all students" +
                "2 : get all groups");
    }

    private void executeOperation(int operationNumber){
        switch (operationNumber) {
            case 1:
                writer.printListOfLinesWithMessageAndSeparation("All students"
                        , studentsService.getAll().stream().map(student -> parser.parseStudentToLine(student))
                                .collect(Collectors.toList()));

        }
    }

    private void printStudent(String message, Student student){

    }

    private void printGroup(String message, Group group){

    }

    private void printStudentList(String message, List<Student> students){
        writer.printListOfLinesWithMessageAndSeparation(message
                , students.stream().map(student -> parser.parseStudentToLine(student))
                        .collect(Collectors.toList()));
    }

    private void printGroupList(String message, List<Group> groups){
        writer.printListOfLinesWithMessageAndSeparation(message
                , groups.stream().map(group -> parser.parseGroupToLine(group))
                        .collect(Collectors.toList()));
    }
}

package menu;

import model.group.Group;
import model.student.Student;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConsoleUserCommunicator implements IUserCommunicator{
    private ConsoleReader reader;
    private ConsoleWriter writer;
    private StudentsService studentsService;
    private GroupsService groupsService;
    private CSVParser parser;
    private boolean stopCommunication = false;

    public ConsoleUserCommunicator(){
        reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 50);
        studentsService = new StudentsService();
        groupsService = new GroupsService();
        parser = new CSVParser();
    }

    @Override
    public void applyUserCommunicationLogic() {
        System.out.println("Write one of the options");
        printOptions();
        while(!stopCommunication){
            writer.printHeader("SYSTEM_REQUEST");
            System.out.print("Option number: ");
            String option = reader.readLine();
            writer.printSeparator();

            try{
                int optionNumber = Integer.parseInt(option);
                executeOption(optionNumber);

            }catch(NumberFormatException exp){
                writer.printLineWithHeaderAndSeparation("ERROR", "Option format unacceptable");
            }
        }


    }

    public void printOptions(){
        writer.printLineWithHeaderAndSeparation("OPTIONS",
                "1 : get all students\n" +
                "2 : get all groups\n" +
                "3 : get student by id\n" +
                "4 : get group by id\n" +
                "5 : add student\n" +
                "6 : add group\n" +
                "7 : update student\n" +
                "8 : update group\n" +
                "9 : delete student\n" +
                "10 : delete group\n" +
                "11 : check if student exist in database\n" +
                "12 : get all students in group\n" +
                "13 : get attendees in group\n" +
                "14 : get online students in group\n" +
                "15 : get all attendees (students with type of studying: present)\n" +
                "16 : get all online students\n" +
                "17 : get all stipends\n" +
                "18 : get all students with type of contract: payable\n" +
                "19 : get all male students\n" +
                "20 : get all female students\n" +
                "21 : get all groups by language\n" +
                "22 : get all groups with online access\n" +
                "23 : get full groups\n" +
                "24 : get all groups with same name");
    }

    private void executeOption(int operationNumber){
        switch (operationNumber) {
            case 1: printStudentList("All students", studentsService.getAll());
                break;
            case 2: printGroupList("All groups", groupsService.getAll());
                break;
            case 3: executeOption_getStudentById();
                break;
            case 4: executeOption_getGroupById();
                break;
            case 5: executeOption_addStudent();
                break;
            case 6: executeOption_addGroup();
                break;
            case 7: stopCommunication();
                break;
            default: writer.printLineWithHeaderAndSeparation("Error", "Command not found");
        }

    }

    private void stopCommunication(){
        stopCommunication = true;
    }

    private void executeOption_addStudent(){
        writer.printHeader("SYSTEM_REQUEST");
        System.out.print("Student ID: ");


    }

    private void executeOption_addGroup(){

    }

    private void executeOption_getStudentById() {
        writer.printHeader("SYSTEM_REQUEST");

        System.out.print("Student ID:");
        String input = reader.readLine();
        writer.printSeparator();

        try {
            int id = Integer.parseInt(input);
            Student student = studentsService.getById(id);
            if (Objects.isNull(student)) {
                writer.printLineWithSeparation("Student with id:" + input + "do not exist");
            } else
                printStudent("Student with id: " + input, student);
        } catch (NumberFormatException exp) {
            writer.printLineWithSeparation("Student with id: " + input + "do not exist");
        }
    }

    private void executeOption_getGroupById() {
        writer.printHeader("SYSTEM_REQUEST");

        System.out.print("Group ID: ");
        String input = reader.readLine();
        writer.printSeparator();

        try {
            int id = Integer.parseInt(input);
            Group group = groupsService.getById(id);

            if (Objects.isNull(group)) {
                writer.printLineWithSeparation("Group with id:" + input + "do not exist");

            } else
                printGroup("Group with id: " + input, group);
        } catch (NumberFormatException exp) {
            writer.printLineWithSeparation("Group with id: " + input + "do not exist");
        }
    }

    private void printStudent(String message, Student student){
        writer.printLineWithHeaderAndSeparation(message, parser.parseStudentToLine(student));
    }

    private void printGroup(String message, Group group){
        writer.printLineWithHeaderAndSeparation(message, parser.parseGroupToLine(group));
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

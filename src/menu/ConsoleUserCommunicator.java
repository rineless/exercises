package menu;

import model.group.Group;
import model.group.GroupNames;
import model.student.Student;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.List;
import java.util.Locale;
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
        System.out.println("Choose one of the options");
        printOptions();
        while(!stopCommunication){
            writer.printHeader("SYSTEM_REQUEST");
            String option = receiveCheckedInputForRequest("Option number: ", "\\d+");

            if(!option.isEmpty())
                executeOption(Integer.parseInt(option));
            else
                stopCommunication();

        }
        reader.close();

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
                "24 : get all groups with same name\n" +
                "   : end");
    }

    private void executeOption(int operationNumber){
        switch (operationNumber) {
            case 1: printStudentList("ALL_STUDENTS", studentsService.getAll());
                break;
            case 2: printGroupList("ALL_GROUPS", groupsService.getAll());
                break;
            case 3: executeOption_getStudentById();
                break;
            case 4: executeOption_getGroupById();
                break;
            case 5: executeOption_addStudent();
                break;
            case 6: executeOption_addGroup();
                break;
            case 7: executeOption_updateStudent();
                break;
            case 8: executeOption_updateGroup();
                break;
            case 9: executeOption_deleteStudent();
                break;
            case 10: executeOption_deleteGroup();
                break;
            case 11: executeOption_studentExist();
                break;
            case 12: executeOption_getAllStudentsInGroup();
                break;
            case 13: executeOption_getAttendeesInGroup();
                break;
            case 14: executeOption_getOnlineStudentsInGroup();
                break;
            case 15: printStudentList("ALL_ATTENDEES_STUDENTS", studentsService.findAttendeesStudents());
                break;
            case 16: printStudentList("ALL_ONLINE_STUDENTS", studentsService.findOnlineStudents());
                break;
            case 17: printStudentList("ALL_STIPENDS", studentsService.findStipends());
                break;
            case 18: printStudentList("ALL_WITH_PAYABLE_CONTRACT", studentsService.findStudentsWithPayableContract());
                break;
            case 19: printStudentList("ALL_MALE_STUDENTS", studentsService.findAllMaleStudents());
                break;
            case 20: printStudentList("ALL_FEMALE_STUDENTS", studentsService.findAllFemaleStudents());
                break;
            case 21: executeOption_getFindAllGroupsByLanguage();
                break;
            case 22: printGroupList("ALL_GROUPS_WITH_ONLINE_ACCESS", groupsService.findAllGroupsWithOnlineAccess(true));
                break;
            case 23: printGroupList("ALL_FULL_GROUPS", groupsService.findFullGroups());
                break;
            case 24: executeOption_getAllGroupsWithSameName();
                break;
            default: writer.printLineWithHeaderAndSeparation("Error", "Command not found");
        }

    }

    private void executeOption_getStudentById() {
        writer.printHeader("GET_STUDENT_BY_ID");
        writer.printHeader("SYSTEM_REQUEST");

        System.out.print("Student ID:");
        String input = reader.readLine();

        if (input.matches("\\d+")) {

            int id = Integer.parseInt(input);
            Student student = studentsService.getById(id);

            if (Objects.isNull(student)) {
                writer.printLineWithSeparation("Student with id:" + input + " do not exist");
            } else
                printStudent("Student with id: " + input, student);
        } else {
            writer.printLineWithSeparation("Student with id: " + input + " do not exist");
        }
    }

    private void executeOption_getGroupById() {
        writer.printHeader("GET_GROUP_BY_ID");
        writer.printHeader("SYSTEM_REQUEST");

        System.out.print("Group ID: ");
        String input = reader.readLine();
        writer.printSeparator();

        if (input.matches("\\d+")) {
            int id = Integer.parseInt(input);
            Group group = groupsService.getById(id);

            if (Objects.isNull(group)) {
                writer.printLineWithSeparation("Group with id:" + input + " do not exist");

            } else
                printGroup("Group with id: " + input, group);
        } else {
            writer.printLineWithSeparation("Group with id: " + input + " do not exist");
        }
    }

    private void executeOption_addStudent(){

        writer.printHeader("ADD_STUDENT");
        writer.printHeader("SYSTEM_REQUEST");
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.add(student);

        else
            System.out.println("Input of student data is interrupted. Student addition is denied");

    }

    private Student receiveStudentFromUserInput(){
        Student student = new Student();

        String id = receiveCheckedInputForRequest("Student ID: ", "\\d+");
        if(!id.isEmpty()){
            student.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest("Name: ", "([a-zA-Z]|\\p{Blank}|'|´)+");
            if(!name.isEmpty()){
                student.setName(name);
                String surname = receiveCheckedInputForRequest("Surname: ", "([a-zA-Z]|\\p{Blank}|'|´)+");
                if(!surname.isEmpty()){
                    student.setSurname(surname);
                    String gender = receiveCheckedInputForRequest("Gender (m or f): ", "m|f");
                    if(!gender.isEmpty()){
                        student.setGender(gender);
                        String birthDate = receiveCheckedInputForRequest("Birth date (__.__.____): "
                                , "((0?[1-9])|((1|2)[0-9])|(3[0-1]))\\.((0?[1-9])|1[0-2])" +
                                        "\\.((19[5-9][0-9])|(20[0-1][0-9]))");
                        if(!birthDate.isEmpty()){
                            student.setBirthDate(birthDate);
                            String citizenship = receiveCheckedInputForRequest("Cititenship: ", "([a-zA-Z]|\\p{Blank}|\\p{Punct})+");
                            if(!citizenship.isEmpty()){
                                student.setCitizenship(citizenship);
                                String placeOfBirth = receiveCheckedInputForRequest("Place of Birth: ", "([a-zA-Z]|\\p{Blank}|\\p{Punct})+");
                                if(!placeOfBirth.isEmpty()){
                                    student.setPlaceOfBirth(placeOfBirth);
                                    String typeOfContract = receiveCheckedInputForRequest("Type of Contract (stipend|payable): ", "stipend|payable");
                                    if(!typeOfContract.isEmpty()){
                                        student.setTypeOfContract(typeOfContract);
                                        String groupId = receiveCheckedInputForRequest("Group Id: ", "\\d+");
                                        if(!groupId.isEmpty()){
                                            student.setGroupId(Integer.parseInt(groupId));
                                            String typeOfStudying = receiveCheckedInputForRequest("Type of studying (present|online): "
                                                    , "present|online");
                                            if(!typeOfStudying.isEmpty()){
                                                student.setTypeOfStudying(typeOfStudying);
                                                String contactInf = receiveCheckedInputForRequest("Contact information: ", ".+");
                                                if(!contactInf.isEmpty()){
                                                    student.setContactInformation(contactInf);
                                                    return student;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        return null;
    }


    private void executeOption_addGroup(){

        writer.printHeader("ADD_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

         if(Objects.nonNull(group))
             groupsService.add(group);

         else
             System.out.println("Input of group data is interrupted. Group addition is denied");


    }

    private Group receiveGroupFromUserInput(){
        Group group = new Group();

        String id = receiveCheckedInputForRequest("Group Id: ", "\\d+");
        if(!id.isEmpty()){
            group.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest("Group name (alg|ds|anl): ", "alg|ds|anl");
            if(!name.isEmpty()){
                group.setGroupName(name);
                String onlineAccess = receiveCheckedInputForRequest("Online access: (true|false)", "true|false");
                if(!onlineAccess.isEmpty()){
                    group.isOnlineAccessible(onlineAccess);
                    System.out.println("Responsible for group");
                    String respName = receiveCheckedInputForRequest("   Name: ", "([a-zA-Z]|\\p{Blank}|'|´)+");
                    String respSurname = receiveCheckedInputForRequest("    Surname: ", "([a-zA-Z]|\\p{Blank}|'|´)+");
                    if(!respName.isEmpty() & !respSurname.isEmpty()){
                        group.setResponsibleForGroup(new String[]{respName, respSurname});
                        String contactInf = receiveCheckedInputForRequest("Contact information: ", ".+");
                        if(!contactInf.isEmpty()){
                            group.setContactInformation(contactInf);
                            return group;
                        }
                    }
                }
            }
        }

        return null;
    }

    private String receiveCheckedInputForRequest(String request, String regex){
        boolean repeatRequest;

        do {
            System.out.print(request);
            String input = reader.readLine();

            if (input.matches(regex)) {
                return input;
            }
            else if (input.contentEquals("")) {
                return "";
            }
            else {
                System.out.println("Forbidden input. To close request leave the field blank");
                repeatRequest = true;
            }

        } while(repeatRequest);

        return "";
    }

    private void executeOption_updateStudent(){
        writer.printHeader("UPDATE_STUDENT");
        writer.printHeader("SYSTEM_REQUEST");
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.update(student);

        else
            System.out.println("Input of student data is interrupted. Student update is denied");
    }

    private void executeOption_updateGroup(){
        writer.printHeader("UPDATE_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(group))
            groupsService.update(group);

        else
            System.out.println("Input of group data is interrupted. Group update is denied");
    }

    private void executeOption_deleteStudent(){
        writer.printHeader("DELETE_STUDENT");
        writer.printHeader("SYSTEM_REQUEST");
        String studentId = receiveCheckedInputForRequest("Student ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            studentsService.delete(studentsService.getById(Integer.parseInt(studentId)));
        }

        else
            System.out.println("Input is interrupted. Nothing will be deleted");
    }

    private void executeOption_deleteGroup(){
        writer.printHeader("DELETE_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        String groupId = receiveCheckedInputForRequest("Group ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            groupsService.delete(groupsService.getById(Integer.parseInt(groupId)));
        }

        else
            System.out.println("Input is interrupted. Nothing will be deleted");
    }

    private void executeOption_studentExist(){
        writer.printHeader("STUDENT_EXIST");
        writer.printHeader("SYSTEM_REQUEST");
        String studentId = receiveCheckedInputForRequest("Student ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            System.out.println(studentsService.studentExists(Integer.parseInt(studentId)) ? "TRUE" : "FALSE");
        }

        else
            System.out.println("Input is interrupted.");
    }

    private void executeOption_getAllStudentsInGroup(){
        writer.printHeader("GET_ALL_STUDENTS_IN_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        String groupId = receiveCheckedInputForRequest("Group ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList("All students in group" + groupId
                    , studentsService.getAllStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println("Input is interrupted.");
    }

    private void executeOption_getAttendeesInGroup(){
        writer.printHeader("GET_ATTENDEES_IN_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        String groupId = receiveCheckedInputForRequest("Group ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList("Attendees in group" + groupId
                        , studentsService.getAttendeesStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println("Input is interrupted.");
    }

    private void executeOption_getOnlineStudentsInGroup(){
        writer.printHeader("GET_ONLINE_STUDENTS_IN_GROUP");
        writer.printHeader("SYSTEM_REQUEST");
        String groupId = receiveCheckedInputForRequest("Group ID: ", "\\d+");
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList("Online students in group: " + groupId
                        , studentsService.getOnlineStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println("Input is interrupted.");
    }

    private void executeOption_getFindAllGroupsByLanguage(){
        writer.printHeader("FIND_ALL_GROUPS_BY_LANGUAGE");
        writer.printHeader("SYSTEM_REQUEST");
        String language = receiveCheckedInputForRequest("Language: ", "[a-zA-Z]+");
        printSeparatorForSystemRequest();

        if(!language.isEmpty()){
           printGroupList("All groups with language: " + language, groupsService.findAllGroupsByLanguage(new Locale(language)));
        }
        else
            System.out.println("Input is interrupted.");
    }

    private void executeOption_getAllGroupsWithSameName(){
        writer.printHeader("FIND_ALL_GROUPS_WITH_SAME_NAME");
        writer.printHeader("SYSTEM_REQUEST");
        String name = receiveCheckedInputForRequest("Group name (alg|anl|ds): ", "alg|anl|ds");
        printSeparatorForSystemRequest();

        if(!name.isEmpty()) {
            printGroupList("All groups with name: " + name, groupsService.findAllGroupsByName(GroupNames.valueOf(name.toUpperCase())));
        }
        else
            System.out.println("Input is interrupted");

    }

    private void stopCommunication(){
        writer.printHeader("END");
        stopCommunication = true;
    }

    private void printStudent(String message, Student student){
        writer.printLineWithHeaderAndSeparation(message, parser.parseStudentToLine(student));
    }

    private void printGroup(String message, Group group){
        writer.printLineWithHeaderAndSeparation(message, parser.parseGroupToLine(group));
    }

    private void printStudentList(String message, List<Student> students){
        if(Objects.nonNull(students))
            writer.printListOfLinesWithMessageAndSeparation(message
                , students.stream().map(student -> parser.parseStudentToLine(student))
                        .collect(Collectors.toList()));
    }

    private void printGroupList(String message, List<Group> groups){
        if(Objects.nonNull(groups))
            writer.printListOfLinesWithMessageAndSeparation(message
                , groups.stream().map(group -> parser.parseGroupToLine(group))
                        .collect(Collectors.toList()));
    }

    private void printSeparatorForSystemRequest(){
        writer.printSeparator(writer.getSeparatorLength() + "SYSTEM_REQUEST".length());
    }
}

package menu;

import model.group.Group;
import model.group.GroupNames;
import model.student.Student;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleUserCommunicator implements IUserCommunicator{
    private final ConsoleReader reader;
    private final ConsoleWriter writer;
    private final StudentsService studentsService;
    private final GroupsService groupsService;
    private final CSVParser parser;
    private boolean stopCommunication = false;

    private ResourceBundle groupProp;
    private ResourceBundle studentProp;
    private ResourceBundle systemCommentsProp;
    private ResourceBundle systemHeadersProp;
    private final ResourceBundle progValues;

    public ConsoleUserCommunicator(){
        reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 100);
        studentsService = new StudentsService();
        groupsService = new GroupsService();
        parser = new CSVParser();

        progValues = ResourceBundle.getBundle("properties.consoleUserCommunicator.valuesForProg", Locale.getDefault());

        groupProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.group", Locale.getDefault());
        studentProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.student", Locale.getDefault());
        systemCommentsProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemComments", Locale.getDefault());
        systemHeadersProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemHeaders", Locale.getDefault());    }

    @Override
    public void applyUserCommunicationLogic() {
        String language = receiveCheckedInputForRequest("Choose language: (en|de) : ", "en|de");
        if(!language.isEmpty()){
                groupProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.group", new Locale(language));
                studentProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.student", new Locale(language));
                systemCommentsProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemComments", new Locale(language));
                systemHeadersProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemHeaders", new Locale(language));

        }
        System.out.println(systemCommentsProp.getString("choose_option"));
        printOptions();


        while(!stopCommunication){
            writer.printHeader(systemHeadersProp.getString("system_request"));
            String option = receiveCheckedInputForRequest(systemCommentsProp.getString("option_number") + ": "
                    , systemCommentsProp.getString("option_number_regex"));

            if(!option.isEmpty())
                executeOption(Integer.parseInt(option));
            else
                stopCommunication();

        }
        reader.close();

    }

    public void printOptions(){
        StringBuilder strBuf = new StringBuilder();
        for(int i = 1 ; i < 25 ; ++i){
           strBuf.append(systemCommentsProp.getString("op" + i));
           strBuf.append("\n");
        }
        strBuf.append(systemCommentsProp.getString("op0"));
        writer.printLineWithHeaderAndSeparation(systemHeadersProp.getString("options"), strBuf.toString());
    }

    private void executeOption(int operationNumber){
        switch (operationNumber) {
            case 1: printStudentList(systemHeadersProp.getString("all_students"), studentsService.getAll());
                break;
            case 2: printGroupList(systemHeadersProp.getString("all_groups"), groupsService.getAll());
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
            case 15: printStudentList(systemHeadersProp.getString("all_attendees_students"), studentsService.findAttendeesStudents());
                break;
            case 16: printStudentList(systemHeadersProp.getString("all_online_students"), studentsService.findOnlineStudents());
                break;
            case 17: printStudentList(systemHeadersProp.getString("all_stipends"), studentsService.findStipends());
                break;
            case 18: printStudentList(systemHeadersProp.getString("all_with_payable_contract"), studentsService.findStudentsWithPayableContract());
                break;
            case 19: printStudentList(systemHeadersProp.getString("all_male_students"), studentsService.findAllMaleStudents());
                break;
            case 20: printStudentList(systemHeadersProp.getString("all_female_students"), studentsService.findAllFemaleStudents());
                break;
            case 21: executeOption_getFindAllGroupsByLanguage();
                break;
            case 22: printGroupList(systemHeadersProp.getString("all_groups_with_online_access"), groupsService.findAllGroupsWithOnlineAccess(true));
                break;
            case 23: printGroupList(systemHeadersProp.getString("all_full_groups"), groupsService.findFullGroups());
                break;
            case 24: executeOption_getAllGroupsWithSameName();
                break;
            default: writer.printLineWithHeaderAndSeparation(systemHeadersProp.getString("error")
                    , systemCommentsProp.getString("command_not_found"));
        }

    }

    private void executeOption_getStudentById() {
        writer.printHeader(systemHeadersProp.getString("get_student_by_id"));
        writer.printHeader(systemHeadersProp.getString("system_request"));

        String input = receiveCheckedInputForRequest(studentProp.getString("student_id") + ": "
                , studentProp.getString("student_id_regex"));

        if (!input.isEmpty()) {

            Student student = studentsService.getById(Integer.parseInt(input));

            if (Objects.isNull(student)) {
                writer.printLineWithSeparation(systemCommentsProp.getString("student_with_id") + input
                        + " " +  systemCommentsProp.getString("do_not_exist"));
            } else
                printStudent(systemCommentsProp.getString("student_with_id") + input, student);
        }
    }

    private void executeOption_getGroupById() {
        writer.printHeader(systemHeadersProp.getString("get_group_by_id"));
        writer.printHeader(systemHeadersProp.getString("system_request"));

        String input = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                , groupProp.getString("group_id_regex"));

        if (!input.isEmpty()) {
            Group group = groupsService.getById(Integer.parseInt(input));

            if (Objects.isNull(group)) {
                writer.printLineWithSeparation(systemCommentsProp.getString("group_with_id") + input
                        + " " + systemCommentsProp.getString("do_not_exist"));

            } else
                printGroup(systemCommentsProp.getString("group_with_id") + input, group);
        }
    }

    private void executeOption_addStudent(){

        writer.printHeader(systemHeadersProp.getString("add_student"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.add(student);

        else
            System.out.println(systemCommentsProp.getString("student_input_interrupted") + " "
                    + systemCommentsProp.getString("student_addition_denied"));

    }

    private Student receiveStudentFromUserInput(){
        Student student = new Student();

        String id = receiveCheckedInputForRequest(studentProp.getString("student_id") + ": ", studentProp.getString("student_id_regex"));
        if(!id.isEmpty()){

            student.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest(studentProp.getString("name") + ": ", studentProp.getString("name_regex"));

            if(!name.isEmpty()){

                student.setName(name);
                String surname = receiveCheckedInputForRequest(studentProp.getString("surname") + ": ", studentProp.getString("surname_regex"));

                if(!surname.isEmpty()){

                    student.setSurname(surname);
                    String gender = receiveCheckedInputForRequest(studentProp.getString("gender") + ": (" + studentProp.getString("gender_regex") + "): "
                            , studentProp.getString("gender_regex"));

                    if(!gender.isEmpty()){

                        List<String> genders = Stream.of(studentProp.getString("gender_regex").split("\\|")).collect(Collectors.toList());
                        gender = progValues.getString("gender" + (genders.indexOf(gender) + 1));
                        student.setGender(gender);
                        String birthDate = receiveCheckedInputForRequest(studentProp.getString("birth_date") + ": ("
                                        + studentProp.getString("birth_date_label") + "): ", studentProp.getString("birth_date_regex"));

                        if(!birthDate.isEmpty()){

                            student.setBirthDate(birthDate);
                            String citizenship = receiveCheckedInputForRequest(studentProp.getString("citizenship") + ": ", studentProp.getString("citizenship_regex"));

                            if(!citizenship.isEmpty()){

                                student.setCitizenship(citizenship);
                                String placeOfBirth = receiveCheckedInputForRequest(studentProp.getString("place_of_birth") + ": ", studentProp.getString("place_of_birth_regex"));

                                if(!placeOfBirth.isEmpty()){

                                    student.setPlaceOfBirth(placeOfBirth);
                                    String typeOfContract = receiveCheckedInputForRequest(studentProp.getString("type_of_contract") + ": ("
                                                    + studentProp.getString("type_of_contract_regex") + "): "
                                            , studentProp.getString("type_of_contract_regex"));

                                    if(!typeOfContract.isEmpty()){

                                        List<String> contractTypes = Stream.of(studentProp.getString("type_of_contract_regex")
                                                .split("\\|")).collect(Collectors.toList());
                                        typeOfContract = progValues.getString("type_of_contract" + (contractTypes.indexOf(typeOfContract) + 1));
                                        student.setTypeOfContract(typeOfContract);
                                        String groupId = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                                                , groupProp.getString("group_id_regex"));

                                        if(!groupId.isEmpty()){

                                            student.setGroupId(Integer.parseInt(groupId));
                                            String typeOfStudying = receiveCheckedInputForRequest(studentProp.getString("type_of_studying") + ": ("
                                                            + studentProp.getString("type_of_studying_regex") + "): "
                                                    , studentProp.getString("type_of_studying_regex"));

                                            if(!typeOfStudying.isEmpty()){

                                                List<String> studyingTypes = Stream.of(studentProp.getString("type_of_studying_regex").split("\\|")).collect(Collectors.toList());
                                                typeOfStudying = progValues.getString("type_of_studying" + (studyingTypes.indexOf(typeOfStudying) + 1));
                                                student.setTypeOfStudying(typeOfStudying);
                                                String contactInf = receiveCheckedInputForRequest(studentProp.getString("contact_inf") + ": "
                                                        , studentProp.getString("contact_inf_regex"));

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

        writer.printHeader(systemHeadersProp.getString("add_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

         if(Objects.nonNull(group))
             groupsService.add(group);

         else
             System.out.println(systemCommentsProp.getString("group_input_interrupted") + " "
                     + systemCommentsProp.getString("group_addition_denied"));


    }

    private Group receiveGroupFromUserInput(){
        Group group = new Group();

        String id = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": ", groupProp.getString("group_id_regex"));
        if(!id.isEmpty()){

            group.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest(groupProp.getString("group_name") + ": (" + groupProp.getString("group_name_regex") + "): "
                    , groupProp.getString("group_name_regex"));

            if(!name.isEmpty()){

                if(groupProp.getString("group_name_regex").contains("|")) {
                    List<String> names = Stream.of(groupProp.getString("group_name_regex").split("\\|")).collect(Collectors.toList());
                    name = progValues.getString("group_name" + (names.indexOf(name) + 1));
                }
                group.setGroupName(name);
                String language = receiveCheckedInputForRequest(groupProp.getString("language") + ": ", groupProp.getString("language_regex"));

                if(!language.isEmpty()) {

                    group.setLanguage(new Locale(language));
                    String onlineAccess = receiveCheckedInputForRequest(groupProp.getString("online_access") + ": (" + groupProp.getString("online_access_regex") + "): "
                            , groupProp.getString("online_access_regex"));

                    if (!onlineAccess.isEmpty()) {

                        onlineAccess = groupProp.getString("online_access_regex").startsWith(onlineAccess) ? "true" : "false";
                        group.isOnlineAccessible(onlineAccess);
                        System.out.println(groupProp.getString("responsible_for_group"));
                        String respName = receiveCheckedInputForRequest(groupProp.getString("name") + ": ", groupProp.getString("name_regex"));
                        String respSurname = receiveCheckedInputForRequest(groupProp.getString("surname") + ": ", groupProp.getString("surname_regex"));

                        if (!respName.isEmpty() & !respSurname.isEmpty()) {

                            group.setResponsibleForGroup(new String[]{respName, respSurname});
                            String contactInf = receiveCheckedInputForRequest(groupProp.getString("contact_information") + ": "
                                    , groupProp.getString("contact_information_regex"));

                            if (!contactInf.isEmpty()) {
                                group.setContactInformation(contactInf);
                                return group;
                            }
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
                System.out.println(systemCommentsProp.getString("forbidden_input"));
                repeatRequest = true;
            }

        } while(repeatRequest);

        return "";
    }

    private void executeOption_updateStudent(){
        writer.printHeader(systemHeadersProp.getString("update_student"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.update(student);

        else
            System.out.println(systemCommentsProp.getString("student_input_interrupted") + " "
                    + systemCommentsProp.getString("student_update_denied"));
    }

    private void executeOption_updateGroup(){
        writer.printHeader(systemHeadersProp.getString("update_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(group))
            groupsService.update(group);

        else
            System.out.println(systemCommentsProp.getString("group_input_interrupted") + " "
                    + systemCommentsProp.getString("group_update_denied"));
    }

    private void executeOption_deleteStudent(){
        writer.printHeader(systemHeadersProp.getString("delete_student"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String studentId = receiveCheckedInputForRequest(studentProp.getString("student_id") + ": "
                , studentProp.getString("student_id_regex"));
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            studentsService.delete(studentsService.getById(Integer.parseInt(studentId)));
        }

        else
            System.out.println(systemCommentsProp.getString("input_interrupted") + " "
                    + systemCommentsProp.getString("nothing_deleted"));
    }

    private void executeOption_deleteGroup(){
        writer.printHeader(systemHeadersProp.getString("delete_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String groupId = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                , groupProp.getString("group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            groupsService.delete(groupsService.getById(Integer.parseInt(groupId)));
        }

        else
            System.out.println(systemCommentsProp.getString("input_interrupted") + " "
                    + systemCommentsProp.getString("nothing_deleted"));
    }

    private void executeOption_studentExist(){
        writer.printHeader(systemHeadersProp.getString("student_exist"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String studentId = receiveCheckedInputForRequest(studentProp.getString("student_id") + ": "
                , studentProp.getString("student_id_regex"));
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            System.out.println(studentsService.studentExists(Integer.parseInt(studentId)) ? systemHeadersProp.getString("true")
                    : systemHeadersProp.getString("false"));
        }

        else
            System.out.println(systemCommentsProp.getString("input_interrupted"));
    }

    private void executeOption_getAllStudentsInGroup(){
        writer.printHeader(systemHeadersProp.getString("get_all_students_in_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String groupId = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                , groupProp.getString("group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(groupProp.getString("students") + groupId
                    , studentsService.getAllStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(systemCommentsProp.getString("input_interrupted"));
    }

    private void executeOption_getAttendeesInGroup(){
        writer.printHeader(systemHeadersProp.getString("get_attendees_in_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String groupId = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                , groupProp.getString("group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(groupProp.getString("attendees") + groupId
                        , studentsService.getAttendeesStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(groupProp.getString("attendees"));
    }

    private void executeOption_getOnlineStudentsInGroup(){
        writer.printHeader(systemHeadersProp.getString("get_online_students_in_group"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String groupId = receiveCheckedInputForRequest(groupProp.getString("group_id") + ": "
                , groupProp.getString("group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(groupProp.getString("online_students") + groupId
                        , studentsService.getOnlineStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(systemCommentsProp.getString("input_interrupted"));
    }

    private void executeOption_getFindAllGroupsByLanguage(){
        writer.printHeader(systemHeadersProp.getString("find_all_groups_by_language"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String language = receiveCheckedInputForRequest(groupProp.getString("language") + ": "
                , groupProp.getString("language_regex"));
        printSeparatorForSystemRequest();

        if(!language.isEmpty()){
           printGroupList(systemCommentsProp.getString("groups_with_language") + language
                   , groupsService.findAllGroupsByLanguage(new Locale(language)));
        }
        else
            System.out.println(systemCommentsProp.getString("input_interrupted"));
    }

    private void executeOption_getAllGroupsWithSameName(){
        writer.printHeader(systemHeadersProp.getString("find_all_groups_with_same_name"));
        writer.printHeader(systemHeadersProp.getString("system_request"));
        String name = receiveCheckedInputForRequest(groupProp.getString("name") + ": "
                , groupProp.getString("name_regex"));
        printSeparatorForSystemRequest();

        if(!name.isEmpty()) {
            printGroupList(systemCommentsProp.getString("groups_with_name") + name
                    , groupsService.findAllGroupsByName(GroupNames.valueOf(name.toUpperCase())));
        }
        else
            System.out.println(systemCommentsProp.getString("input_interrupted"));

    }

    private void stopCommunication(){
        writer.printHeader(systemHeadersProp.getString("end"));
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
                , students.stream().map(parser::parseStudentToLine)
                        .collect(Collectors.toList()));
    }

    private void printGroupList(String message, List<Group> groups){
        if(Objects.nonNull(groups))
            writer.printListOfLinesWithMessageAndSeparation(message
                , groups.stream().map(parser::parseGroupToLine)
                        .collect(Collectors.toList()));
    }

    private void printSeparatorForSystemRequest(){
        writer.printSeparator();
    }
}

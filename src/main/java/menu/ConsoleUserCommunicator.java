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

    private  ResourceBundle progValues;

    public ConsoleUserCommunicator(){
        reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 100);
        studentsService = new StudentsService();
        groupsService = new GroupsService();
        parser = new CSVParser();

        progValues = ResourceBundle.getBundle("properties.valuesForProg", Locale.getDefault());
    }

    @Override
    public void applyUserCommunicationLogic() {
        String language = receiveCheckedInputForRequest("Choose language: (en|de) : ", "en|de");
        if(!language.isEmpty()){
                progValues = ResourceBundle.getBundle("properties.valuesForProg", new Locale(language));
        }
        System.out.println(progValues.getString("consoleUserCommunicator.choose_option"));
        printOptions();


        while(!stopCommunication){
            writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
            String option = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.option_number")
                    , progValues.getString("consoleUserCommunicator.option_number_regex"));

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
           strBuf.append(progValues.getString("consoleUserCommunicator.op" + i));
           strBuf.append("\n");
        }
        strBuf.append(progValues.getString("consoleUserCommunicator.op0"));
        writer.printLineWithHeaderAndSeparation(progValues.getString("consoleUserCommunicator.options"), strBuf.toString());
    }

    private void executeOption(int operationNumber){
        switch (operationNumber) {
            case 1: printStudentList(progValues.getString("consoleUserCommunicator.all_students"), studentsService.getAll());
                break;
            case 2: printGroupList(progValues.getString("consoleUserCommunicator.all_groups"), groupsService.getAll());
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
            case 15: printStudentList(progValues.getString("consoleUserCommunicator.all_attendees_students")
                    , studentsService.findAttendeesStudents());
                break;
            case 16: printStudentList(progValues.getString("consoleUserCommunicator.all_online_students")
                    , studentsService.findOnlineStudents());
                break;
            case 17: printStudentList(progValues.getString("consoleUserCommunicator.all_stipends")
                    , studentsService.findStipends());
                break;
            case 18: printStudentList(progValues.getString("consoleUserCommunicator.all_with_payable_contract")
                    , studentsService.findStudentsWithPayableContract());
                break;
            case 19: printStudentList(progValues.getString("consoleUserCommunicator.all_male_students")
                    , studentsService.findAllMaleStudents());
                break;
            case 20: printStudentList(progValues.getString("consoleUserCommunicator.all_female_students")
                    , studentsService.findAllFemaleStudents());
                break;
            case 21: executeOption_getFindAllGroupsByLanguage();
                break;
            case 22: printGroupList(progValues.getString("consoleUserCommunicator.all_groups_with_online_access")
                    , groupsService.findAllGroupsWithOnlineAccess(true));
                break;
            case 23: printGroupList(progValues.getString("consoleUserCommunicator.all_full_groups")
                    , groupsService.findFullGroups());
                break;
            case 24: executeOption_getAllGroupsWithSameName();
                break;
            default: writer.printLineWithHeaderAndSeparation(progValues.getString("consoleUserCommunicator.error")
                    , progValues.getString("consoleUserCommunicator.command_not_found"));
        }

    }

    private void executeOption_getStudentById() {
        writer.printHeader(progValues.getString("consoleUserCommunicator.get_student_by_id"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));

        String input = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_id")
                , progValues.getString("consoleUserCommunicator.student_id_regex"));

        if (!input.isEmpty()) {

            Student student = studentsService.getById(Integer.parseInt(input));

            if (Objects.isNull(student)) {
                writer.printLineWithSeparation(progValues.getString("consoleUserCommunicator.student_with_id") + input
                        + " " +  progValues.getString("consoleUserCommunicator.do_not_exist"));
            } else
                printStudent(progValues.getString("consoleUserCommunicator.student_with_id") + input, student);
        }
    }

    private void executeOption_getGroupById() {
        writer.printHeader(progValues.getString("consoleUserCommunicator.get_group_by_id"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));

        String input = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));

        if (!input.isEmpty()) {
            Group group = groupsService.getById(Integer.parseInt(input));

            if (Objects.isNull(group)) {
                writer.printLineWithSeparation(progValues.getString("consoleUserCommunicator.group_with_id") + input
                        + " " + progValues.getString("consoleUserCommunicator.do_not_exist"));

            } else
                printGroup(progValues.getString("consoleUserCommunicator.group_with_id") + input, group);
        }
    }

    private void executeOption_addStudent(){

        writer.printHeader(progValues.getString("consoleUserCommunicator.add_student"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.add(student);

        else
            System.out.println(progValues.getString("consoleUserCommunicator.student_input_interrupted") + " "
                    + progValues.getString("consoleUserCommunicator.student_addition_denied"));

    }

    private Student receiveStudentFromUserInput(){
        Student student = new Student();

        String id = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_id")
                , progValues.getString("consoleUserCommunicator.student_id_regex"));
        if(!id.isEmpty()){

            student.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_name")
                    , progValues.getString("consoleUserCommunicator.student_name_regex"));

            if(!name.isEmpty()){

                student.setName(name);
                String surname = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_surname")
                        , progValues.getString("consoleUserCommunicator.student_surname_regex"));

                if(!surname.isEmpty()){

                    student.setSurname(surname);
                    String gender = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.gender") + "(" + progValues.getString("gender_regex") + "): "
                            , progValues.getString("consoleUserCommunicator.gender_regex"));

                    if(!gender.isEmpty()){

                        List<String> genders = Stream.of(progValues.getString("consoleUserCommunicator.gender_regex").split("\\|")).collect(Collectors.toList());
                        gender = progValues.getString("consoleUserCommunicator.gender" + (genders.indexOf(gender) + 1));
                        student.setGender(gender);
                        String birthDate = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.birth_date") + "("
                                        + progValues.getString("consoleUserCommunicator.birth_date_label") + "): ", progValues.getString("consoleUserCommunicator.birth_date_regex"));

                        if(!birthDate.isEmpty()){

                            student.setBirthDate(birthDate);
                            String citizenship = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.citizenship"),
                                    progValues.getString("consoleUserCommunicator.citizenship_regex"));

                            if(!citizenship.isEmpty()){

                                student.setCitizenship(citizenship);
                                String placeOfBirth = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.place_of_birth")
                                        , progValues.getString("consoleUserCommunicator.place_of_birth_regex"));

                                if(!placeOfBirth.isEmpty()){

                                    student.setPlaceOfBirth(placeOfBirth);
                                    String typeOfContract = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.type_of_contract") + "("
                                                    + progValues.getString("consoleUserCommunicator.type_of_contract_regex") + "): "
                                            , progValues.getString("consoleUserCommunicator.type_of_contract_regex"));

                                    if(!typeOfContract.isEmpty()){

                                        List<String> contractTypes = Stream.of(progValues.getString("consoleUserCommunicator.type_of_contract_regex")
                                                .split("\\|")).collect(Collectors.toList());
                                        typeOfContract = progValues.getString("consoleUserCommunicator.type_of_contract" + (contractTypes.indexOf(typeOfContract) + 1));
                                        student.setTypeOfContract(typeOfContract);
                                        String groupId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                                                , progValues.getString("consoleUserCommunicator.group_id_regex"));

                                        if(!groupId.isEmpty()){

                                            student.setGroupId(Integer.parseInt(groupId));
                                            String typeOfStudying = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.type_of_studying") + "("
                                                            + progValues.getString("consoleUserCommunicator.type_of_studying_regex") + "): "
                                                    , progValues.getString("consoleUserCommunicator.type_of_studying_regex"));

                                            if(!typeOfStudying.isEmpty()){

                                                List<String> studyingTypes = Stream.of(progValues.getString("consoleUserCommunicator.type_of_studying_regex").split("\\|")).collect(Collectors.toList());
                                                typeOfStudying = progValues.getString("consoleUserCommunicator.type_of_studying" + (studyingTypes.indexOf(typeOfStudying) + 1));
                                                student.setTypeOfStudying(typeOfStudying);
                                                String contactInf = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.contact_inf")
                                                        , progValues.getString("consoleUserCommunicator.contact_inf_regex"));

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

        writer.printHeader(progValues.getString("consoleUserCommunicator.add_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

         if(Objects.nonNull(group))
             groupsService.add(group);

         else
             System.out.println(progValues.getString("consoleUserCommunicator.group_input_interrupted")
                     + progValues.getString("consoleUserCommunicator.group_addition_denied"));


    }

    private Group receiveGroupFromUserInput(){
        Group group = new Group();

        String id = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));
        if(!id.isEmpty()){

            group.setId(Integer.parseInt(id));
            String name = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_name") + "("
                            + progValues.getString("consoleUserCommunicator.group_name_regex") + "): "
                    , progValues.getString("consoleUserCommunicator.group_name_regex"));

            if(!name.isEmpty()){

                if(progValues.getString("consoleUserCommunicator.group_name_regex").contains("|")) {
                    List<String> names = Stream.of(progValues.getString("consoleUserCommunicator.group_name_regex").split("\\|")).collect(Collectors.toList());
                    name = progValues.getString("consoleUserCommunicator.group_name" + (names.indexOf(name) + 1));
                }
                group.setGroupName(name);
                String language = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.language")
                        , progValues.getString("consoleUserCommunicator.language_regex"));

                if(!language.isEmpty()) {

                    group.setLanguage(new Locale(language));
                    String onlineAccess = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.online_access") + "("
                                    + progValues.getString("consoleUserCommunicator.online_access_regex") + "): "
                            , progValues.getString("consoleUserCommunicator.online_access_regex"));

                    if (!onlineAccess.isEmpty()) {

                        onlineAccess = progValues.getString("consoleUserCommunicator.online_access_regex").startsWith(onlineAccess) ? "true" : "false";
                        group.isOnlineAccessible(onlineAccess);
                        System.out.println(progValues.getString("consoleUserCommunicator.responsible_for_group"));
                        String respName = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.resp_name")
                                , progValues.getString("consoleUserCommunicator.resp_name_regex"));
                        String respSurname = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.resp_surname")
                                , progValues.getString("consoleUserCommunicator.resp_surname_regex"));

                        if (!respName.isEmpty() & !respSurname.isEmpty()) {

                            group.setResponsibleForGroup(new String[]{respName, respSurname});
                            String contactInf = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.contact_information") + ": "
                                    , progValues.getString("consoleUserCommunicator.contact_information_regex"));

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
                System.out.println(progValues.getString("consoleUserCommunicator.forbidden_input"));
                repeatRequest = true;
            }

        } while(repeatRequest);

        return "";
    }

    private void executeOption_updateStudent(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.update_student"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        Student student = receiveStudentFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(student))
            studentsService.update(student);

        else
            System.out.println(progValues.getString("consoleUserCommunicator.student_input_interrupted") + " "
                    + progValues.getString("consoleUserCommunicator.student_update_denied"));
    }

    private void executeOption_updateGroup(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.update_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        Group group = receiveGroupFromUserInput();
        printSeparatorForSystemRequest();

        if(Objects.nonNull(group))
            groupsService.update(group);

        else
            System.out.println(progValues.getString("consoleUserCommunicator.group_input_interrupted")
                    + progValues.getString("consoleUserCommunicator.group_update_denied"));
    }

    private void executeOption_deleteStudent(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.delete_student"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String studentId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_id")
                , progValues.getString("consoleUserCommunicator.student_id_regex"));
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            studentsService.delete(studentsService.getById(Integer.parseInt(studentId)));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted")
                    + progValues.getString("consoleUserCommunicator.nothing_deleted"));
    }

    private void executeOption_deleteGroup(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.delete_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String groupId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            groupsService.delete(groupsService.getById(Integer.parseInt(groupId)));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted")
                    + progValues.getString("consoleUserCommunicator.nothing_deleted"));
    }

    private void executeOption_studentExist(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.student_exist"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String studentId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.student_id")
                , progValues.getString("consoleUserCommunicator.student_id_regex"));
        printSeparatorForSystemRequest();

        if(!studentId.isEmpty()){
            System.out.println(studentsService.studentExists(Integer.parseInt(studentId)) ? progValues.getString("consoleUserCommunicator.true")
                    : progValues.getString("consoleUserCommunicator.false"));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted"));
    }

    private void executeOption_getAllStudentsInGroup(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.get_all_students_in_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String groupId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(progValues.getString("consoleUserCommunicator.students") + groupId
                    , studentsService.getAllStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted"));
    }

    private void executeOption_getAttendeesInGroup(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.get_attendees_in_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String groupId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(progValues.getString("consoleUserCommunicator.attendees") + groupId
                        , studentsService.getAttendeesStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.attendees"));
    }

    private void executeOption_getOnlineStudentsInGroup(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.get_online_students_in_group"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String groupId = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_id")
                , progValues.getString("consoleUserCommunicator.group_id_regex"));
        printSeparatorForSystemRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                printStudentList(progValues.getString("consoleUserCommunicator.online_students") + groupId
                        , studentsService.getOnlineStudentsInGroup(Integer.parseInt(groupId)));
        }

        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted"));
    }

    private void executeOption_getFindAllGroupsByLanguage(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.find_all_groups_by_language"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String language = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.language")
                , progValues.getString("consoleUserCommunicator.language_regex"));
        printSeparatorForSystemRequest();

        if(!language.isEmpty()){
           printGroupList(progValues.getString("consoleUserCommunicator.groups_with_language") + language
                   , groupsService.findAllGroupsByLanguage(new Locale(language)));
        }
        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted"));
    }

    private void executeOption_getAllGroupsWithSameName(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.find_all_groups_with_same_name"));
        writer.printHeader(progValues.getString("consoleUserCommunicator.system_request"));
        String name = receiveCheckedInputForRequest(progValues.getString("consoleUserCommunicator.group_name")
                , progValues.getString("consoleUserCommunicator.group_name_regex"));
        printSeparatorForSystemRequest();

        if(!name.isEmpty()) {
            printGroupList(progValues.getString("consoleUserCommunicator.groups_with_name") + name
                    , groupsService.findAllGroupsByName(GroupNames.valueOf(name.toUpperCase())));
        }
        else
            System.out.println(progValues.getString("consoleUserCommunicator.input_interrupted"));

    }

    private void stopCommunication(){
        writer.printHeader(progValues.getString("consoleUserCommunicator.end"));
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

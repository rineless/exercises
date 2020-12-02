package menu.util.options;

import menu.util.communicator.ConsoleCommunicator;
import menu.util.communicator.ICommunicator;
import model.group.Group;
import model.group.GroupNames;
import model.student.Student;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsExecutor {
    static private ICommunicator communicator = new ConsoleCommunicator();

    static private ResourceBundle properties= ResourceBundle.getBundle("properties.valuesForProg", Locale.getDefault());
    static private StudentsService studentsService = new StudentsService();
    static private GroupsService groupsService = new GroupsService();
    static private CSVParser parser = new CSVParser();


    public static void execute(Options options){
        if(Objects.nonNull(options)) {
            switch (options) {
                case GET_ALL_STUDENTS: executeOption_getAllStudents();
                    break;
                case GET_ALL_GROUPS: executeOption_getAllGroups();
                    break;
                case GET_STUDENT_BY_ID: executeOption_getStudentById();
                    break;
                case GET_GROUP_BY_ID: executeOption_getGroupById();
                    break;
                case ADD_STUDENT: executeOption_addStudent();
                    break;
                case ADD_GROUP: executeOption_addGroup();
                    break;
                case UPDATE_STUDENT: executeOption_updateStudent();
                    break;
                case UPDATE_GROUP: executeOption_updateGroup();
                    break;
                case DELETE_STUDENT: executeOption_deleteStudent();
                    break;
                case DELETE_GROUP: executeOption_deleteGroup();
                    break;
                case STUDENT_EXIST: executeOption_studentExist();
                    break;
                case GET_ALL_STUDENTS_IN_GROUP: executeOption_getAllStudentsInGroup();
                    break;
                case GET_ALL_ATTENDEES_STUDENTS: executeOption_getAllAttendeesStudents();
                    break;
                case GET_ALL_FEMALE_STUDENTS: executeOption_getAllFemaleStudents();
                    break;
                case GET_ALL_GROUPS_BY_LANGUAGE: executeOption_getAllGroupsByLanguage();
                    break;
                case GET_ALL_GROUPS_WITH_SAME_NAME: executeOption_getAllGroupsWithSameName();
                    break;
                case GET_ALL_MALE_STUDENTS: executeOption_getAllMaleStudents();
                    break;
                case GET_ALL_ONLINE_STUDENTS: executeOption_getAllOnlineStudents();
                    break;
                case GET_ALL_STIPENDS: executeOption_getAllStipends();
                    break;
                case GET_ALL_STUDENTS_WITH_PAYABLE_CONTRACT: executeOption_getAllStudentsWithPayableContract();
                    break;
                case GET_ATTENDEES_IN_GROUP: executeOption_getAttendeesInGroup();
                    break;
                case GET_FULL_GROUPS: executeOption_getFullGroups();
                    break;
                case GET_GROUPS_WITH_ONLINE_ACCESS: executeOption_getGroupsWithOnlineAccess();
                    break;
                case GET_ONLINE_STUDENTS_IN_GROUP: executeOption_getOnlineStudentsInGroup();
                    break;
            }
        }
    }

    private static void executeOption_getGroupsWithOnlineAccess() {
        communicator.endRequest(properties.getString("all_groups_with_online_access")
                , groupsService.findAllGroupsWithOnlineAccess(true).stream()
                        .map(parser::parseGroupToLine)
                        .collect(Collectors.toList()));
    }

    private static void executeOption_getFullGroups() {
        communicator.endRequest(properties.getString("all_full_groups"), groupsService.findFullGroups().stream()
                .map(parser::parseGroupToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllStudentsWithPayableContract() {
        communicator.endRequest(properties.getString("all_with_payable_contract")
                , studentsService.findStudentsWithPayableContract().stream()
                        .map(parser::parseStudentToLine)
                        .collect(Collectors.toList()));
    }

    private static void executeOption_getAllStipends() {
        communicator.endRequest(properties.getString("all_stipends"), studentsService.findStipends().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllOnlineStudents() {
        communicator.endRequest(properties.getString("all_online_students"), studentsService.findOnlineStudents().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllMaleStudents() {
        communicator.endRequest(properties.getString("all_male_students"), studentsService.findAllMaleStudents().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllFemaleStudents() {
        communicator.endRequest(properties.getString("all_female_students"), studentsService.findAllFemaleStudents().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllGroups() {
        communicator.endRequest(properties.getString("all_groups"), groupsService.getAll().stream()
                .map(parser::parseGroupToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllStudents() {
        communicator.endRequest(properties.getString("all_students"), studentsService.getAll().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }

    private static void executeOption_getAllAttendeesStudents() {
        communicator.endRequest(properties.getString("all_attendees_students"), studentsService.findAttendeesStudents().stream()
                .map(parser::parseStudentToLine)
                .collect(Collectors.toList()));
    }


    private static void executeOption_getStudentById() {
        communicator.startRequest(properties.getString("get_student_by_id"));

        String input = communicator.receiveCheckedInputForRequest(properties.getString("student_id") + ": "
                , properties.getString("student_id_regex"));

        if (!input.isEmpty()) {

            Student student = studentsService.getById(Integer.parseInt(input));

            if (Objects.isNull(student)) {
                communicator.endRequest(properties.getString("student_with_id") + input
                        + " " +  properties.getString("do_not_exist"));
            } else
                communicator.endRequest(properties.getString("student_with_id") + input
                        , parser.parseStudentToLine(student));
        }
    }

    private static void executeOption_getGroupById() {
        communicator.startRequest(properties.getString("get_group_by_id"));

        String input = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                , properties.getString("group_id_regex"));

        if (!input.isEmpty()) {
            Group group = groupsService.getById(Integer.parseInt(input));

            if (Objects.isNull(group)) {
                communicator.endRequest(properties.getString("group_with_id") + input
                        + " " + properties.getString("do_not_exist"));

            } else
                communicator.endRequest(properties.getString("group_with_id") + input
                        , parser.parseGroupToLine(group));
        }
    }

    private static void executeOption_addStudent(){

        communicator.startRequest(properties.getString("add_student"));
        Student student = receiveStudentFromUserInput();
        communicator.endRequest();

        if(Objects.nonNull(student))
            studentsService.add(student);

        else
            communicator.interruptRequest(properties.getString("student_input_interrupted") + " "
                    + properties.getString("student_addition_denied"));

    }

    private static Student receiveStudentFromUserInput(){
        Student student = new Student();

        String id = communicator.receiveCheckedInputForRequest(properties.getString("student_id") + ": ", properties.getString("student_id_regex"));
        if(!id.isEmpty()){

            student.setId(Integer.parseInt(id));
            String name = communicator.receiveCheckedInputForRequest(properties.getString("name") + ": ", properties.getString("name_regex"));

            if(!name.isEmpty()){

                student.setName(name);
                String surname = communicator.receiveCheckedInputForRequest(properties.getString("surname") + ": ", properties.getString("surname_regex"));

                if(!surname.isEmpty()){

                    student.setSurname(surname);
                    String gender = communicator.receiveCheckedInputForRequest(properties.getString("gender") + ": (" + properties.getString("gender_regex") + "): "
                            , properties.getString("gender_regex"));

                    if(!gender.isEmpty()){

                        List<String> genders = Stream.of(properties.getString("gender_regex").split("\\|")).collect(Collectors.toList());
                        gender = properties.getString("gender" + (genders.indexOf(gender) + 1));
                        student.setGender(gender);
                        String birthDate = communicator.receiveCheckedInputForRequest(properties.getString("birth_date") + ": ("
                                + properties.getString("birth_date_label") + "): ", properties.getString("birth_date_regex"));

                        if(!birthDate.isEmpty()){

                            student.setBirthDate(birthDate);
                            String citizenship = communicator.receiveCheckedInputForRequest(properties.getString("citizenship") + ": ", properties.getString("citizenship_regex"));

                            if(!citizenship.isEmpty()){

                                student.setCitizenship(citizenship);
                                String placeOfBirth = communicator.receiveCheckedInputForRequest(properties.getString("place_of_birth") + ": ", properties.getString("place_of_birth_regex"));

                                if(!placeOfBirth.isEmpty()){

                                    student.setPlaceOfBirth(placeOfBirth);
                                    String typeOfContract = communicator.receiveCheckedInputForRequest(properties.getString("type_of_contract") + ": ("
                                                    + properties.getString("type_of_contract_regex") + "): "
                                            , properties.getString("type_of_contract_regex"));

                                    if(!typeOfContract.isEmpty()){

                                        List<String> contractTypes = Stream.of(properties.getString("type_of_contract_regex")
                                                .split("\\|")).collect(Collectors.toList());
                                        typeOfContract = properties.getString("type_of_contract" + (contractTypes.indexOf(typeOfContract) + 1));
                                        student.setTypeOfContract(typeOfContract);
                                        String groupId = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                                                , properties.getString("group_id_regex"));

                                        if(!groupId.isEmpty()){

                                            student.setGroupId(Integer.parseInt(groupId));
                                            String typeOfStudying = communicator.receiveCheckedInputForRequest(properties.getString("type_of_studying") + ": ("
                                                            + properties.getString("type_of_studying_regex") + "): "
                                                    , properties.getString("type_of_studying_regex"));

                                            if(!typeOfStudying.isEmpty()){

                                                List<String> studyingTypes = Stream.of(properties.getString("type_of_studying_regex").split("\\|")).collect(Collectors.toList());
                                                typeOfStudying = properties.getString("type_of_studying" + (studyingTypes.indexOf(typeOfStudying) + 1));
                                                student.setTypeOfStudying(typeOfStudying);
                                                String contactInf = communicator.receiveCheckedInputForRequest(properties.getString("contact_inf") + ": "
                                                        , properties.getString("contact_inf_regex"));

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


    private static void executeOption_addGroup(){

        communicator.startRequest(properties.getString("add_group"));
        Group group = receiveGroupFromUserInput();
        communicator.endRequest();

        if(Objects.nonNull(group))
            groupsService.add(group);

        else
            communicator.interruptRequest(properties.getString("group_input_interrupted") + " "
                    + properties.getString("group_addition_denied"));


    }

    private static Group receiveGroupFromUserInput(){
        Group group = new Group();

        String id = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": ", properties.getString("group_id_regex"));
        if(!id.isEmpty()){

            group.setId(Integer.parseInt(id));
            String name = communicator.receiveCheckedInputForRequest(properties.getString("group_name") + ": (" + properties.getString("group_name_regex") + "): "
                    , properties.getString("group_name_regex"));

            if(!name.isEmpty()){

                if(properties.getString("group_name_regex").contains("|")) {
                    List<String> names = Stream.of(properties.getString("group_name_regex").split("\\|")).collect(Collectors.toList());
                    name = properties.getString("group_name" + (names.indexOf(name) + 1));
                }
                group.setGroupName(name);
                String language = communicator.receiveCheckedInputForRequest(properties.getString("language") + ": ", properties.getString("language_regex"));

                if(!language.isEmpty()) {

                    group.setLanguage(new Locale(language));
                    String onlineAccess = communicator.receiveCheckedInputForRequest(properties.getString("online_access") + ": (" + properties.getString("online_access_regex") + "): "
                            , properties.getString("online_access_regex"));

                    if (!onlineAccess.isEmpty()) {

                        onlineAccess = properties.getString("online_access_regex").startsWith(onlineAccess) ? "true" : "false";
                        group.isOnlineAccessible(onlineAccess);
                        communicator.requestAddition(properties.getString("responsible_for_group"));
                        String respName = communicator.receiveCheckedInputForRequest(properties.getString("name") + ": ", properties.getString("name_regex"));
                        String respSurname = communicator.receiveCheckedInputForRequest(properties.getString("surname") + ": ", properties.getString("surname_regex"));

                        if (!respName.isEmpty() & !respSurname.isEmpty()) {

                            group.setResponsibleForGroup(new String[]{respName, respSurname});
                            String contactInf = communicator.receiveCheckedInputForRequest(properties.getString("contact_information") + ": "
                                    , properties.getString("contact_information_regex"));

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

    private static void executeOption_updateStudent(){
        communicator.startRequest(properties.getString("update_student"));
        Student student = receiveStudentFromUserInput();
        communicator.endRequest();

        if(Objects.nonNull(student))
            studentsService.update(student);

        else
            communicator.interruptRequest(properties.getString("student_input_interrupted") + " "
                    + properties.getString("student_update_denied"));
    }

    private static void executeOption_updateGroup(){
        communicator.startRequest(properties.getString("update_group"));
        Group group = receiveGroupFromUserInput();
        communicator.endRequest();

        if(Objects.nonNull(group))
            groupsService.update(group);

        else
            communicator.interruptRequest(properties.getString("group_input_interrupted") + " "
                    + properties.getString("group_update_denied"));
    }

    private static void executeOption_deleteStudent(){
        communicator.startRequest(properties.getString("delete_student"));
        String studentId = communicator.receiveCheckedInputForRequest(properties.getString("student_id") + ": "
                , properties.getString("student_id_regex"));
        communicator.endRequest();

        if(!studentId.isEmpty()){
            studentsService.delete(studentsService.getById(Integer.parseInt(studentId)));
        }

        else
            communicator.interruptRequest(properties.getString("input_interrupted") + " "
                    + properties.getString("nothing_deleted"));
    }

    private static void executeOption_deleteGroup(){
        communicator.startRequest(properties.getString("delete_group"));
        String groupId = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                , properties.getString("group_id_regex"));
        communicator.endRequest();

        if(!groupId.isEmpty()){
            groupsService.delete(groupsService.getById(Integer.parseInt(groupId)));
        }

        else
            communicator.interruptRequest(properties.getString("input_interrupted") + " "
                    + properties.getString("nothing_deleted"));
    }

    private static void executeOption_studentExist(){
        communicator.startRequest(properties.getString("student_exist"));
        String studentId = communicator.receiveCheckedInputForRequest(properties.getString("student_id") + ": "
                , properties.getString("student_id_regex"));
        communicator.endRequest();

        if(!studentId.isEmpty()){
            communicator.endRequest(studentsService.studentExists(Integer.parseInt(studentId)) ? properties.getString("true")
                    : properties.getString("false"));
        }

        else
            communicator.interruptRequest(properties.getString("input_interrupted"));
    }

    private static void executeOption_getAllStudentsInGroup(){
        communicator.startRequest(properties.getString("get_all_students_in_group"));
        String groupId = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                , properties.getString("group_id_regex"));
        communicator.endRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                communicator.endRequest(properties.getString("students") + groupId
                        , studentsService.getAllStudentsInGroup(Integer.parseInt(groupId)).stream()
                                .map(parser::parseStudentToLine)
                        .collect(Collectors.toList()));
        }

        else
            communicator.interruptRequest(properties.getString("input_interrupted"));
    }

    private static void executeOption_getAttendeesInGroup(){
        communicator.startRequest(properties.getString("get_attendees_in_group"));
        String groupId = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                , properties.getString("group_id_regex"));
        communicator.endRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                communicator.endRequest(properties.getString("attendees") + groupId
                        , studentsService.getAttendeesStudentsInGroup(Integer.parseInt(groupId)).stream()
                                .map(parser::parseStudentToLine)
                                .collect(Collectors.toList()));
        }

        else
            communicator.interruptRequest(properties.getString("attendees"));
    }

    private static void executeOption_getOnlineStudentsInGroup(){
        communicator.startRequest(properties.getString("get_online_students_in_group"));
        String groupId = communicator.receiveCheckedInputForRequest(properties.getString("group_id") + ": "
                , properties.getString("group_id_regex"));
        communicator.endRequest();

        if(!groupId.isEmpty()){
            if(Objects.nonNull(groupsService.getById(Integer.parseInt(groupId))))
                communicator.endRequest(properties.getString("online_students") + groupId
                        , studentsService.getOnlineStudentsInGroup(Integer.parseInt(groupId)).stream()
                                .map(parser::parseStudentToLine)
                                .collect(Collectors.toList()));
        }

        else
            communicator.interruptRequest(properties.getString("input_interrupted"));
    }

    private static void executeOption_getAllGroupsByLanguage(){
        communicator.startRequest(properties.getString("find_all_groups_by_language"));
        String language = communicator.receiveCheckedInputForRequest(properties.getString("language") + ": "
                , properties.getString("language_regex"));
        communicator.endRequest();

        if(!language.isEmpty()){
            communicator.endRequest(properties.getString("groups_with_language") + language
                    , groupsService.findAllGroupsByLanguage(new Locale(language)).stream()
                            .map(parser::parseGroupToLine)
                            .collect(Collectors.toList()));
        }
        else
            communicator.interruptRequest(properties.getString("input_interrupted"));
    }

    private static void executeOption_getAllGroupsWithSameName(){
        communicator.startRequest(properties.getString("find_all_groups_with_same_name"));
        String name = communicator.receiveCheckedInputForRequest(properties.getString("name") + ": "
                , properties.getString("name_regex"));
        communicator.endRequest();

        if(!name.isEmpty()) {
            communicator.endRequest(properties.getString("groups_with_name") + name
                    , groupsService.findAllGroupsByName(GroupNames.valueOf(name.toUpperCase())).stream()
                            .map(parser::parseGroupToLine)
                            .collect(Collectors.toList()));
        }
        else
            communicator.interruptRequest(properties.getString("input_interrupted"));

    }

}

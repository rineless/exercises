package menu.util.options;

import menu.util.communicator.ICommunicator;
import model.group.Group;
import model.group.GroupNames;
import model.student.Student;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsExecutor {
    private ICommunicator communicator;

    public OptionsExecutor(ICommunicator communicator, Locale locale){
        //TODO
    }

    public static void execute(Options options){
        if(Objects.nonNull(options)) {

            switch (options) {
                case GET_ALL_STUDENTS:
                    break;
                case GET_ALL_GROUPS:
                    break;
                case GET_STUDENT_BY_ID:
                    break;
                case GET_GROUP_BY_ID:
                    break;
                case ADD_STUDENT:
                    break;
                case ADD_GROUP:
                    break;
                case UPDATE_STUDENT:
                    break;
                case UPDATE_GROUP:
                    break;
                case DELETE_STUDENT:
                    break;
                case DELETE_GROUP:
                    break;
                case STUDENT_EXIST:
                    break;
                case GET_ALL_STUDENTS_IN_GROUP:
                    break;
                case GET_ALL_ATTENDEES_STUDENTS:
                    break;
                case GET_ALL_FEMALE_STUDENTS:
                    break;
                case GET_ALL_GROUPS_BY_LANGUAGE:
                    break;
                case GET_ALL_GROUPS_WITH_SAME_NAME:
                    break;
                case GET_ALL_MALE_STUDENTS:
                    break;
                case GET_ALL_ONLINE_STUDENTS:
                    break;
                case GET_ALL_STIPENDS:
                    break;
                case GET_ALL_STUDENTS_WITH_PAYABLE_CONTRACT:
                    break;
                case GET_ATTENDEES_IN_GROUP:
                    break;
                case GET_FULL_GROUPS:
                    break;
                case GET_GROUPS_WITH_ONLINE_ACCESS:
                    break;
                case GET_ONLINE_STUDENTS_IN_GROUP:
                    break;
            }
        }
    }

    //TODO execution methods for each option

    private void executeOption_getStudentById() {
        communicator.startRequest(systemHeadersProp.getString("get_student_by_id"));
        writer.printHeader(systemHeadersProp.getString("get_student_by_id"));
        writer.printHeader(systemHeadersProp.getString("system_request"));

        String input = communicator.receiveCheckedInputForRequest(studentProp.getString("student_id") + ": "
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

}

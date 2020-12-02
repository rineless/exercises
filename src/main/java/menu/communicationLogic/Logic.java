package menu.communicationLogic;

import menu.util.communicator.ConsoleCommunicator;
import menu.util.communicator.ICommunicator;
import service.GroupsService;
import service.StudentsService;
import util.parser.CSVParser;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.io.BufferedReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class Logic {
    //TODO properties
    private ResourceBundle properties = ResourceBundle.getBundle("", Locale.getDefault());

    private ICommunicator communicator;
    private boolean stopCommunication = false;

    public Logic() {
        /*reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 100);
        studentsService = new StudentsService();
        groupsService = new GroupsService();
        parser = new CSVParser();*/
        communicator = new ConsoleCommunicator();

    }

    @Override
    public void applyUserCommunicationLogic() {
        String language = communicator.receiveCheckedInputForRequest("Choose language: (en|de) : ", "en|de");
        if(!language.isEmpty()){
            Locale.setDefault(new Locale(language));
            properties = ResourceBundle.getBundle("properties.consoleUserCommunicator.group", new Locale(language));

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
}

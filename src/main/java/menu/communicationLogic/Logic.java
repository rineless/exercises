package menu.communicationLogic;

import menu.util.communicator.ConsoleCommunicator;
import menu.util.communicator.ICommunicator;
import menu.util.options.Options;
import menu.util.options.OptionsExecutor;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Logic {
    //TODO properties
    private ResourceBundle properties = ResourceBundle.getBundle("properties.valuesForProg", Locale.getDefault());

    private ICommunicator communicator;
    private boolean stopCommunication = false;

    public Logic() {
        communicator = new ConsoleCommunicator();

    }

    public void applyUserCommunicationLogic() {
        String language = communicator.receiveCheckedInputForRequest("Choose language: (en|de) : ", "en|de");
        if(!language.isEmpty()){
            Locale.setDefault(new Locale(language));
            properties = ResourceBundle.getBundle("properties.consoleUserCommunicator.group", new Locale(language));

        }
        communicator.startRequest(properties.getString("choose_option"));
        printOptions();


        while(!stopCommunication){
            String option = communicator.receiveCheckedInputForRequest(properties.getString("option_number") + ": "
                    , properties.getString("option_number_regex"));

            if(!option.isEmpty())
                executeOption(Integer.parseInt(option));
            else
                stopCommunication();

        }
        communicator.close();

    }

    private void stopCommunication(){
        communicator.endRequest(properties.getString("end"));
        stopCommunication = true;
    }

    public void printOptions(){
        StringBuilder strBuf = new StringBuilder();
        for(int i = 1 ; i < 25 ; ++i){
            strBuf.append(properties.getString("op" + i));
            strBuf.append("\n");
        }
        strBuf.append(properties.getString("op0"));
        communicator.endRequest(properties.getString("options"), strBuf.toString());
    }

    private void executeOption(int operationNumber){
        Options option;

        switch (operationNumber) {
            case 1: option = Options.GET_ALL_STUDENTS;
                break;
            case 2: option = Options.GET_ALL_GROUPS;
                break;
            case 3: option = Options.GET_STUDENT_BY_ID;
                break;
            case 4: option = Options.GET_GROUP_BY_ID;
                break;
            case 5: option = Options.ADD_STUDENT;
                break;
            case 6: option = Options.ADD_GROUP;
                break;
            case 7: option = Options.UPDATE_STUDENT;
                break;
            case 8: option = Options.UPDATE_GROUP;
                break;
            case 9: option = Options.DELETE_STUDENT;
                break;
            case 10: option = Options.DELETE_GROUP;
                break;
            case 11: option = Options.STUDENT_EXIST;
                break;
            case 12: option = Options.GET_ALL_STUDENTS_IN_GROUP;
                break;
            case 13: option = Options.GET_ATTENDEES_IN_GROUP;
                break;
            case 14: option = Options.GET_ONLINE_STUDENTS_IN_GROUP;
                break;
            case 15: option = Options.GET_ALL_ATTENDEES_STUDENTS;
                break;
            case 16: option = Options.GET_ALL_ONLINE_STUDENTS;
                break;
            case 17: option = Options.GET_ALL_STIPENDS;
                break;
            case 18: option = Options.GET_ALL_STUDENTS_WITH_PAYABLE_CONTRACT;
                break;
            case 19: option = Options.GET_ALL_MALE_STUDENTS;
                break;
            case 20: option = Options.GET_ALL_FEMALE_STUDENTS;
                break;
            case 21: option = Options.GET_ALL_GROUPS_BY_LANGUAGE;
                break;
            case 22: option = Options.GET_GROUPS_WITH_ONLINE_ACCESS;
                break;
            case 23: option = Options.GET_FULL_GROUPS;
                break;
            case 24: option = Options.GET_ALL_GROUPS_WITH_SAME_NAME;
                break;
            default: option = null;

            if(Objects.nonNull(option))
                OptionsExecutor.execute(option);
            else
                communicator.endRequest(properties.getString("error")
                        , properties.getString("command_not_found"));
        }

    }
}

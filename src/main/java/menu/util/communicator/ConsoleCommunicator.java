package menu.util.communicator;

import model.student.Student;
import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConsoleCommunicator implements ICommunicator {
    private final ConsoleReader reader;
    private final ConsoleWriter writer;

    private ResourceBundle properties;

    public ConsoleCommunicator(){
        reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 100);

        properties = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemComments", Locale.getDefault());
    }

    public void startRequest(String request){
        writer.printHeader(request);
        writer.printHeader(properties.getString("system_request"));
    }

    public String receiveCheckedInputForRequest(String request, String regex) {
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
                System.out.println(properties.getString("forbidden_input"));
                repeatRequest = true;
            }

        } while(repeatRequest);

        return "";
    }

    public void endRequest(String message, String result) {
        writer.printLineWithHeaderAndSeparation(message, result);
    }

    public void endRequest(String message){
        writer.printLineWithSeparation(message);
    }

    public void endRequest(){
        writer.printSeparator();
    }

    public void interruptRequest(String message){
        System.out.println(message);
    }

    public void endRequest(String message, List<String> list){
        if(Objects.nonNull(list))
            writer.printListOfLinesWithMessageAndSeparation(message, list);
    }

    public void requestAddition(String message){
        System.out.println(message);
    }
}

package menu.util.communicator;

import util.reader.ConsoleReader;
import util.writer.ConsoleWriter;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleCommunicator implements ICommunicator {
    private final ConsoleReader reader;
    private final ConsoleWriter writer;
    private final Locale locale;

    private ResourceBundle systemCommentsProp;
    private ResourceBundle systemHeadersProp;

    public ConsoleCommunicator(Locale locale){
        reader = new ConsoleReader();
        writer = new ConsoleWriter("-", 100);
        this.locale = locale;

        systemCommentsProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemComments", locale);
        systemHeadersProp = ResourceBundle.getBundle("properties.consoleUserCommunicator.system.systemHeaders", locale);
    }

    public Locale receiveLocale() {
        return locale;
    }

    public void startRequest(String request){
        writer.printHeader(request);
        writer.printHeader(systemHeadersProp.getString("system_request"));
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
                System.out.println(systemCommentsProp.getString("forbidden_input"));
                repeatRequest = true;
            }

        } while(repeatRequest);

        return "";
    }

    public void endRequest() {

    }
}

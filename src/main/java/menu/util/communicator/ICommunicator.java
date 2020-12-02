package menu.util.communicator;

import java.util.List;

public interface ICommunicator {
    void startRequest(String request);
    String receiveCheckedInputForRequest(String request, String regex);
    void endRequest(String message, List<String> list);
    void endRequest(String message, String result);
    void endRequest(String message);
    void endRequest();
    void interruptRequest(String message);
    void requestAddition(String message);
    void close();
}

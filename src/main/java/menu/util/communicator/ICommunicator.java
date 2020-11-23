package menu.util.communicator;

public interface ICommunicator {
    void startRequest(String request);
    String receiveCheckedInputForRequest(String request, String regex);
    void endRequest();
}

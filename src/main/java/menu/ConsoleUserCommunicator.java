package menu;

import menu.communicationLogic.Logic;
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

public class ConsoleUserCommunicator {
    private Logic logic;

    public ConsoleUserCommunicator() {
        logic = new Logic();
    }

    public void startCommunication(){
        logic.applyUserCommunicationLogic();
    }
}

package util;

import model.*;

public interface LineParser {
    String[] parseLineToArray(String line);

    //TODO if no contact information?
    default Student parseLineToStudent(String line) {
        String[] dataForStudent = parseLineToArray(line);
        if (dataForStudent.length == 11)
            return new Student(Integer.parseInt(dataForStudent[0]), dataForStudent[1]
                    , dataForStudent[2], dataForStudent[3], dataForStudent[4], dataForStudent[5]
                    , dataForStudent[6], dataForStudent[7], Integer.parseInt(dataForStudent[8])
                    , dataForStudent[9], dataForStudent[10]);
        else {
            throw new IllegalArgumentException("Line cannot be resolved into Student. Not enough data");
        }
    }


    //TODO
    default Group parseLineToGroup(String line) {
        String[] dataForGroup = parseLineToArray(line);
        if (dataForGroup.length == 10)
            return new Group(Integer.parseInt(dataForGroup[0]), dataForGroup[1], dataForGroup[2], dataForGroup[3]
                    , dataForGroup[4], Integer.parseInt(dataForGroup[5]), Integer.parseInt(dataForGroup[6])
                    , Integer.parseInt(dataForGroup[7]), dataForGroup[8], dataForGroup[9]);
        else {
            throw new IllegalArgumentException("Line cannot be resolved into Group. Not enough data");
        }
    }



}

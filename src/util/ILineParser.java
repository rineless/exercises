package util;

import model.group.Group;
import model.student.Student;

public interface ILineParser {
    String[] parseLineToArray(String line);

    default Student parseLineToStudent(String line) {
        String[] dataForStudent = parseLineToArray(line);
        if (dataForStudent.length == 11)
            return new Student().setId(Integer.parseInt(dataForStudent[0])).setName(dataForStudent[1])
                    .setSurname(dataForStudent[2]).setGender(dataForStudent[3]).setBirthDate(dataForStudent[4])
                    .setCitizenship(dataForStudent[5]).setPlaceOfBirth(dataForStudent[6])
                    .setTypeOfContract(dataForStudent[7]).setGroupId(Integer.parseInt(dataForStudent[8]))
                    .setTypeOfStudying(dataForStudent[9]).setContractInformation(dataForStudent[10]);
        else {
            throw new IllegalArgumentException("Line cannot be resolved into Student. Not enough data");
        }
    }


    default Group parseLineToGroup(String line) {
        String[] dataForGroup = parseLineToArray(line);
        if (dataForGroup.length == 10)
            return new Group().setId(Integer.parseInt(dataForGroup[0])).setName(dataForGroup[1])
                    .setAbbreviation(dataForGroup[2]).setLanguage(dataForGroup[3]).setOnlineAccess(dataForGroup[4])
                    .setMaxAttendeesPresent(Integer.parseInt(dataForGroup[7])).setResponsibleForGroup(dataForGroup[8])
                    .setContactInformation(dataForGroup[9]);
        else {
            throw new IllegalArgumentException("Line cannot be resolved into Group. Not enough data");
        }
    }


}

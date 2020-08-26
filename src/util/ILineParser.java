package util;

import model.group.Group;
import model.student.Student;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ILineParser {
    String[] parseLineToArray(String line);
    String parseArrayToLine(String[] array);

    //TODO add exceptions
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

    //TODO add exceptions
    default Group parseLineToGroup(String line) {
        String[] dataForGroup = parseLineToArray(line);
        if (dataForGroup.length == 8)
            return new Group().setId(Integer.parseInt(dataForGroup[0])).setName(dataForGroup[1])
                    .setAbbreviation(dataForGroup[2]).setLanguage(dataForGroup[3]).setOnlineAccess(dataForGroup[4])
                    .setMaxAttendeesPresent(Integer.parseInt(dataForGroup[5])).setResponsibleForGroup(dataForGroup[6])
                    .setContactInformation(dataForGroup[7]);
        else {
            throw new IllegalArgumentException("Line cannot be resolved into Group. Not enough data");
        }
    }

    default String parseStudentToLine(Student student){
        String[] studentData = new String[11];
        studentData[0] = String.valueOf(student.getId());
        studentData[1] = student.getName();
        studentData[2] = student.getSurname();
        studentData[3] = student.getGender().toString();
        LocalDate date = student.getBirthDate();
        studentData[4] = String.valueOf(date.getDayOfMonth())+"."+String.valueOf(date.getMonthValue())
                +"."+String.valueOf(date.getYear());
        studentData[5] = student.getCitizenship();
        studentData[6] = student.getPlaceOfBirth();
        studentData[7] = student.getTypeOfContract().toString();
        studentData[8] = String.valueOf(student.getGroupId());
        studentData[9] = student.getTypeOfStudying().toString();
        studentData[10] = student.getContractInformation();
        return parseArrayToLine(studentData);
    }

    default String parseGroupToLine(Group group) {
        String[] groupData = new String[8];
        groupData[0] = String.valueOf(group.getId());
        groupData[1] = group.getName();
        groupData[2] = group.getAbbreviation().toString();
        groupData[3] = group.getLanguage().toString();
        groupData[4] = String.valueOf(group.getOnlineAccess()); //TODO check
        groupData[5] = String.valueOf(group.getMaxAttendeesPresent());
        String responsibleForGroup = Stream.of(group.getResponsibleForGroup())
                .map(name -> name + " ").collect(Collectors.joining());
        groupData[6] = responsibleForGroup.substring(0, responsibleForGroup.length() - 1);//TODO check
        groupData[7] = group.getContactInformation();
        return parseArrayToLine(groupData);
    }


}

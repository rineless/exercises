package util.parser;

import model.group.Group;
import model.student.Student;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ILineParser {
    String[] parseLineToArray(String line);
    String parseArrayToLine(String[] array);


    default Student parseLineToStudent(String line) {
        if (line != null) {
            String[] dataForStudent = parseLineToArray(line);
            if (dataForStudent.length == 11)
                try {
                    return new Student().setId(Integer.parseInt(dataForStudent[0])).setName(dataForStudent[1])
                            .setSurname(dataForStudent[2]).setGender(dataForStudent[3]).setBirthDate(dataForStudent[4])
                            .setCitizenship(dataForStudent[5]).setPlaceOfBirth(dataForStudent[6])
                            .setTypeOfContract(dataForStudent[7]).setGroupId(Integer.parseInt(dataForStudent[8]))
                            .setTypeOfStudying(dataForStudent[9]).setContactInformation(dataForStudent[10]);
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    throw new IllegalArgumentException("Line cannot be resolved into Student. Incorrect input of student birth date");
                }
            else {
                throw new IllegalArgumentException("Line cannot be resolved into Student. Not enough data");
            }
        } else
            throw new IllegalArgumentException("Null cannot be resolved into Student.");

    }

    default Group parseLineToGroup(String line) {
        if(line != null) {
            String[] dataForGroup = parseLineToArray(line);
            if (dataForGroup.length == 7)
                return new Group().setId(Integer.parseInt(dataForGroup[0]))
                        .setGroupName(dataForGroup[1]).setLanguage(dataForGroup[2]).isOnlineAccessible(dataForGroup[3])
                        .setMaxAttendeesPresent(Integer.parseInt(dataForGroup[4])).setResponsibleForGroup(dataForGroup[5])
                        .setContactInformation(dataForGroup[6]);
            else {
                throw new IllegalArgumentException("Line cannot be resolved into Group. Not enough data");
            }
        }
        throw new IllegalArgumentException("Null cannot be resolved into group");
    }

    default String parseStudentToLine(Student student){
        if(student == null)
            return "";

        String[] studentData = new String[11];
        studentData[0] = student.getId() == 0 ? "" : String.valueOf(student.getId());
        studentData[1] = student.getName();
        studentData[2] = student.getSurname();
        studentData[3] = student.getGender() == null ? null : student.getGender().toString();
        LocalDate date = student.getBirthDate();
        studentData[4] = date == null ? null : String.valueOf(date.getDayOfMonth())+"."+String.valueOf(date.getMonthValue())
                +"."+String.valueOf(date.getYear());
        studentData[5] = student.getCitizenship();
        studentData[6] = student.getPlaceOfBirth();
        studentData[7] = student.getTypeOfContract() == null ? null : student.getTypeOfContract().toString();
        studentData[8] = student.getGroupId() == 0 ? "" : String.valueOf(student.getGroupId());
        studentData[9] = student.getTypeOfStudying() == null ? null : student.getTypeOfStudying().toString();
        studentData[10] = student.getContactInformation();
        return parseArrayToLine(studentData);
    }

    default String parseGroupToLine(Group group) {
        if(group == null)
            return "";

        String[] groupData = new String[7];
        groupData[0] = group.getId() == 0 ? "" : String.valueOf(group.getId());
        groupData[1] = group.getGroupName() == null ? null : group.getGroupName().toString();
        groupData[2] = group.getLanguage() == null ? null : group.getLanguage().toString();
        groupData[3] = String.valueOf(group.isOnlineAccessible());
        groupData[4] = group.getMaxAttendeesPresent() == 0 ? "" : String.valueOf(group.getMaxAttendeesPresent());
        String responsibleForGroup = (group.getResponsibleForGroup() == null | group.getResponsibleForGroup().length < 1) ? " "
                : Stream.of(group.getResponsibleForGroup())
                .map(name -> name + " ").collect(Collectors.joining());
        groupData[5] = responsibleForGroup.substring(0, responsibleForGroup.length() - 1);
        groupData[6] = group.getContactInformation();
        return parseArrayToLine(groupData);
    }


}

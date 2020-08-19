package model;

public class Group {
    private final int id;
    private final String name;
    private final String abbreviation;
    private final String language;
    private final boolean onlineAccess;
    private final int attendeesOnline;
    private final int attendeesPresent;
    private final int maxAttendeesPresent;
    private final String[] responsibleForGroup;
    private final String contactInformation;

    //TODO check if attributes are valid
    public Group(int id, String name, String abbreviation, String language
            , boolean onlineAccess, int attendeesOnline, int attendeesPresent
            , int maxAttendeesPresent, String responsibleForGroup, String contactInformation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.language = language;
        this.onlineAccess = onlineAccess;
        this.attendeesOnline = attendeesOnline;
        this.attendeesPresent = attendeesPresent;
        this.maxAttendeesPresent = maxAttendeesPresent;
        this.responsibleForGroup = responsibleForGroup.split(" ");//TODO check
        this.contactInformation = contactInformation;
    }

}

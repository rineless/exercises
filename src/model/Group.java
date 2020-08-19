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
            , String onlineAccess, int attendeesOnline, int attendeesPresent
            , int maxAttendeesPresent, String responsibleForGroup, String contactInformation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.language = language;
        if(onlineAccess.contentEquals("yes"))
            this.onlineAccess = true;
        else if(onlineAccess.contentEquals("no"))
            this.onlineAccess = false;
        else throw new IllegalArgumentException("Illegal argument: online access");
        this.attendeesOnline = attendeesOnline;
        this.attendeesPresent = attendeesPresent;
        this.maxAttendeesPresent = maxAttendeesPresent;
        this.responsibleForGroup = responsibleForGroup.split(" ");//TODO check, maybe create separator
        this.contactInformation = contactInformation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isOnlineAccess() {
        return onlineAccess;
    }

    public int getAttendeesOnline() {
        return attendeesOnline;
    }

    public int getAttendeesPresent() {
        return attendeesPresent;
    }

    public int getMaxAttendeesPresent() {
        return maxAttendeesPresent;
    }

    public String[] getResponsibleForGroup() {
        return new String[]{responsibleForGroup[0],responsibleForGroup[1]};
    }

    public String getContactInformation() {
        return contactInformation;
    }


}

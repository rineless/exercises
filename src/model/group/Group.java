package model.group;

import util.SeparatedValuesParser;

import java.util.Locale;

public class Group {

    private int id;
    private String name;
    private Abbreviation abbreviation;
    private Locale language;
    private boolean onlineAccess;
    private int maxAttendeesPresent;
    private String[] responsibleForGroup;
    private String contactInformation;

    //TODO check if attributes are valid
    /*public Group(int id, String name, String abbreviation, String language
            , String onlineAccess, int attendeesOnline, int attendeesPresent
            , int maxAttendeesPresent, String responsibleForGroup, String contactInformation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.language = language;
        if (onlineAccess.contentEquals("yes"))
            this.onlineAccess = true;
        else if (onlineAccess.contentEquals("no"))
            this.onlineAccess = false;
        else throw new IllegalArgumentException("Illegal argument: online access");
        this.attendeesOnline = attendeesOnline;
        this.attendeesPresent = attendeesPresent;
        this.maxAttendeesPresent = maxAttendeesPresent;
        this.responsibleForGroup = new SeparatedValuesParser(responsibleForGroup).parseLineToArray("");
        this.contactInformation = contactInformation;
    }*/

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public Group setAbbreviation(String abbreviation) {
        this.abbreviation = Abbreviation.valueOf(abbreviation);
        return this;
    }

    public Group setLanguage(String language) {
        this.language = new Locale(language);
        return this;
    }

    public Group setOnlineAccess(String onlineAccess) {
        if (onlineAccess.contentEquals("yes"))
            this.onlineAccess = true;
        else
            this.onlineAccess = false;
        return this;
    }

    public Group setMaxAttendeesPresent(int maxAttendeesPresent) {
        this.maxAttendeesPresent = maxAttendeesPresent;
        return this;
    }

    public Group setResponsibleForGroup(String responsibleForGroup) {
        this.responsibleForGroup = new SeparatedValuesParser(responsibleForGroup).parseLineToArray("");
        return this;
    }

    public Group setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Abbreviation getAbbreviation() {
        return abbreviation;
    }

    public Locale getLanguage() {
        return language;
    }

    public boolean isOnlineAccess() {
        return onlineAccess;
    }

    public int getMaxAttendeesPresent() {
        return maxAttendeesPresent;
    }

    public String[] getResponsibleForGroup() {
        return new String[]{responsibleForGroup[0], responsibleForGroup[1]};
    }

    public String getContactInformation() {
        return contactInformation;
    }


}

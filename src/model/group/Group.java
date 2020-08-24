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

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public Group setAbbreviation(String abbreviation) {
        this.abbreviation = Abbreviation.valueOf(abbreviation.toUpperCase());
        return this;
    }

    public Group setLanguage(String language) {
        this.language = new Locale(language);
        return this;
    }
    //TODO check if exceptions
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
        this.responsibleForGroup = new SeparatedValuesParser(responsibleForGroup).parseLineToArray(" ");
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

    public boolean getOnlineAccess() {
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

    public String toString(){
        return new SeparatedValuesParser(", ").parseGroupToLine(this);
    }


}

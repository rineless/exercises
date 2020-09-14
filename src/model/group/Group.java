package model.group;

import util.parser.SeparatedValuesParser;

import java.util.Arrays;
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
        if(name != null) {
            if(name != "") {
                this.name = name;
                return this;
            }
        }

        throw new IllegalArgumentException("Empty name or null is prohibited");
    }

    public Group setAbbreviation (String abbreviation) {
        this.abbreviation = Abbreviation.valueOf(abbreviation.toUpperCase());
        return this;
    }

    public Group setAbbreviation(Abbreviation abbreviation){
        if(abbreviation != null) {
            this.abbreviation = abbreviation;
            return this;
        }

        throw new IllegalArgumentException("Null as abbreviation is prohibited");
    }

    public Group setLanguage(String language) {
        this.language = new Locale(language);
        return this;
    }

    public Group setLanguage(Locale language){
        if(language != null) {
            this.language = language;
            return this;
        }

        throw new IllegalArgumentException("Null as language is prohibited");
    }

    //TODO check if exceptions
    public Group setOnlineAccess(String onlineAccess) {
        if(onlineAccess != null) {
            if (onlineAccess.toLowerCase().contentEquals("yes")) {
                this.onlineAccess = true;
                return this;
            }
            else if (onlineAccess.toLowerCase().contentEquals("no")){
                this.onlineAccess = false;
                return this;
            }
        }

        throw new IllegalArgumentException("Online access input is incorrect. Should be one of yes|YES|Yes|no|NO|No");
    }

    public Group setOnlineAccess(boolean onlineAccess){
        this.onlineAccess = onlineAccess;
        return this;
    }

    public Group setMaxAttendeesPresent(int maxAttendeesPresent) {
        this.maxAttendeesPresent = maxAttendeesPresent;
        return this;
    }

    public Group setResponsibleForGroup(String responsibleForGroup) {
        if(responsibleForGroup != null) {
            String[] array = new SeparatedValuesParser(" ").parseLineToArray(responsibleForGroup);
            if(array.length == 2) {
                this.responsibleForGroup = new String[]{array[0], array[1]};
                return this;
            }
        }

        throw new IllegalArgumentException("Input cannot be resolved into responsible for group");
    }

    public Group setResponsibleForGroup(String[] responsibleForGroup){
        if(responsibleForGroup != null){
            if(responsibleForGroup.length == 2){
                this.responsibleForGroup = new String[]{responsibleForGroup[0], responsibleForGroup[1]};
                return this;
            }
        }

        throw new IllegalArgumentException("Input cannot be resolved into responsible for group");
    }

    public Group setContactInformation(String contactInformation) {
        if(contactInformation != null) {
            this.contactInformation = contactInformation;
            return this;
        }

        throw new IllegalArgumentException("Null cannot be resolved into contact information");
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

    @Override
    public boolean equals(Object obj){
        if(obj != null){
            if(obj instanceof Group)
                return equals((Group) obj);
        }

        return false;
    }

    public boolean equals(Group group){
        try {
            if (id == group.getId() && name.contentEquals(group.getName()) && abbreviation == group.getAbbreviation()
                    && language.equals(group.getLanguage()) && onlineAccess == group.getOnlineAccess()
                    && maxAttendeesPresent == group.getMaxAttendeesPresent()
                    && Arrays.compare(responsibleForGroup, group.getResponsibleForGroup()) == 0
                    && contactInformation.contentEquals(group.getContactInformation()))
                return true;
        }
        catch(NullPointerException exp){
            return false;
        }

        return false;
    }

}

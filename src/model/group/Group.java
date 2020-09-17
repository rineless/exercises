package model.group;

import util.parser.SeparatedValuesParser;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public Group setName(String name) throws IllegalArgumentException {
        if (name != null) {
            if (!name.contentEquals("")) {
                this.name = name;
                return this;
            }
        }

        throw new IllegalArgumentException("Empty name or null is prohibited");
    }

    public Group setAbbreviation (String abbreviation) throws IllegalArgumentException{
        this.abbreviation = Abbreviation.valueOf(abbreviation.toUpperCase());
        return this;
    }

    public Group setAbbreviation(Abbreviation abbreviation) throws IllegalArgumentException{
        if(abbreviation != null) {
            this.abbreviation = abbreviation;
            return this;
        }

        throw new IllegalArgumentException("Null as abbreviation is prohibited");
    }

    public Group setLanguage(String language) throws IllegalArgumentException{
        if(language!=null) {
            if(!language.contentEquals("")) {
                this.language = new Locale(language);
                return this;
            }
        }

        throw new IllegalArgumentException("Empty language or null is prohibited");
    }

    public Group setLanguage(Locale language) throws IllegalArgumentException{
        if(language != null) {
            this.language = language;
            return this;
        }

        throw new IllegalArgumentException("Null as language is prohibited");
    }

    public Group setOnlineAccess(String onlineAccess) throws IllegalArgumentException{
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

    public Group setResponsibleForGroup(String responsibleForGroup) throws IllegalArgumentException{
        if(responsibleForGroup != null) {
            String[] array = new SeparatedValuesParser(" ").parseLineToArray(responsibleForGroup);
                this.responsibleForGroup = Arrays.copyOf(array, array.length);
                return this;
        }

        throw new IllegalArgumentException("Input cannot be resolved into responsible for group");
    }

    public Group setResponsibleForGroup(String[] responsibleForGroup) throws IllegalArgumentException{
        if(responsibleForGroup != null){
            this.responsibleForGroup = Arrays.copyOf(responsibleForGroup,responsibleForGroup.length);
            return this;
        }

        throw new IllegalArgumentException("Input cannot be resolved into responsible for group");
    }

    public Group setContactInformation(String contactInformation) throws IllegalArgumentException{
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
        if(responsibleForGroup != null) {
            return Arrays.copyOf(responsibleForGroup,responsibleForGroup.length);
        }
        return new String[]{};
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

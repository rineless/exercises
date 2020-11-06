package model.group;

import util.parser.SeparatedValuesParser;

import java.util.Arrays;
import java.util.Locale;

public class Group {

    private int id;
    private GroupNames groupName;
    private Locale language;
    private boolean isOnlineAccessible;
    private int maxAttendeesPresent;
    private String[] responsibleForGroup;
    private String contactInformation;

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Group setGroupName(String groupName) throws IllegalArgumentException{
        if(groupName != null) {
            this.groupName = GroupNames.valueOf(groupName.toUpperCase());
            return this;
        }

        throw new IllegalArgumentException("Null as group name is prohibited");
    }

    public Group setGroupName(GroupNames groupName) throws IllegalArgumentException{
        if(groupName != null) {
            this.groupName = groupName;
            return this;
        }

        throw new IllegalArgumentException("Null as group name is prohibited");
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

    public Group isOnlineAccessible(String isOnlineAccessible) throws IllegalArgumentException{
        if(isOnlineAccessible != null) {
            if (isOnlineAccessible.toLowerCase().contentEquals("true")) {
                this.isOnlineAccessible = true;
                return this;
            }
            else if (isOnlineAccessible.toLowerCase().contentEquals("false")){
                this.isOnlineAccessible = false;
                return this;
            }
        }

        throw new IllegalArgumentException("Online access input is incorrect. Should be one of yes|YES|Yes|no|NO|No");
    }

    public Group isOnlineAccessible(boolean isOnlineAccessible){
        this.isOnlineAccessible = isOnlineAccessible;
        return this;
    }

    public Group setMaxAttendeesPresent(int maxAttendeesPresent) {
        this.maxAttendeesPresent = maxAttendeesPresent;
        return this;
    }

    public Group setResponsibleForGroup(String responsibleForGroup) throws IllegalArgumentException{
        if(responsibleForGroup != null) {
            if(!responsibleForGroup.contentEquals("")) {
                String[] array = new SeparatedValuesParser(" ").parseLineToArray(responsibleForGroup);
                this.responsibleForGroup = Arrays.copyOf(array, array.length);
                return this;
            }
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

    public String getFullName() {
        if(groupName != null)
            return groupName.getFullName();

        return "";
    }

    public GroupNames getGroupName() {
        return groupName;
    }

    public Locale getLanguage() {
        return language;
    }

    public boolean isOnlineAccessible() {
        return isOnlineAccessible;
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
            if (id == group.getId() && groupName == group.getGroupName()
                    && language.equals(group.getLanguage()) && isOnlineAccessible == group.isOnlineAccessible()
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

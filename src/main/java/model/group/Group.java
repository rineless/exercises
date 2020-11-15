package model.group;

import util.parser.SeparatedValuesParser;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Group {

    private int id;
    private GroupNames groupName;
    private Locale language;
    private boolean isOnlineAccessible;
    private int maxAttendeesPresent;
    private String[] responsibleForGroup;
    private String contactInformation;

    private final ResourceBundle properties = ResourceBundle.getBundle("properties.model.group.group", Locale.getDefault());

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Group setGroupName(String groupName) throws IllegalArgumentException{
        if(groupName != null) {
            if(groupName.toUpperCase().matches(properties.getString("name_regex"))) {
                this.groupName = GroupNames.valueOf(groupName.toUpperCase());
                return this;
            }

            throw new IllegalArgumentException(properties.getString("incorrect_name"));
        }

        throw new IllegalArgumentException(properties.getString("null_name"));
    }

    public Group setGroupName(GroupNames groupName) throws IllegalArgumentException{
        if(groupName != null) {
            this.groupName = groupName;
            return this;
        }

        throw new IllegalArgumentException(properties.getString("null_name"));
    }

    public Group setLanguage(String language) throws IllegalArgumentException{
        if(language!=null) {
                this.language = new Locale(language);
                return this;
        }

        throw new IllegalArgumentException(properties.getString("null_language"));
    }

    public Group setLanguage(Locale language) throws IllegalArgumentException{
        if(language != null) {
            this.language = language;
            return this;
        }

        throw new IllegalArgumentException(properties.getString("null_language"));
    }

    public Group isOnlineAccessible(String isOnlineAccessible) throws IllegalArgumentException{
        if(isOnlineAccessible != null) {
            if(isOnlineAccessible.toLowerCase().matches(properties.getString("onlineAccess_regex"))) {
                if (isOnlineAccessible.toLowerCase().contentEquals("true")) {
                    this.isOnlineAccessible = true;
                } else {
                    this.isOnlineAccessible = false;
                }
                return this;
            }

            throw new IllegalArgumentException(properties.getString("incorrect_onlineAccess"));
        }

        throw new IllegalArgumentException(properties.getString("null_onlineAccess"));
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
            if(responsibleForGroup.matches(properties.getString("responsible_regex"))) {
                String[] array = new SeparatedValuesParser(" ").parseLineToArray(responsibleForGroup);
                this.responsibleForGroup = Arrays.copyOf(array, array.length);
                return this;
            }

            throw new IllegalArgumentException(properties.getString("incorrect_responsible"));
        }

        throw new IllegalArgumentException(properties.getString("null_responsible"));
    }

    public Group setResponsibleForGroup(String[] responsibleForGroup) throws IllegalArgumentException{
        if(responsibleForGroup != null){
            this.responsibleForGroup = Arrays.copyOf(responsibleForGroup,responsibleForGroup.length);
            return this;
        }

        throw new IllegalArgumentException(properties.getString("null_responsible"));
    }

    public Group setContactInformation(String contactInformation) throws IllegalArgumentException{
        if(contactInformation != null) {
            this.contactInformation = contactInformation;
            return this;
        }

        throw new IllegalArgumentException(properties.getString("null_contactInf"));
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

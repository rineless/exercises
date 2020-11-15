package model.group;

//By adding new names, change Group.java setGroupName method and properties for group (name_regex)
public enum GroupNames {
    DS("Discrete structures"), ALG("Algorithms"), ANL("Analysis");

     private final String fullName;

    GroupNames(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}

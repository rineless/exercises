package model.group;

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

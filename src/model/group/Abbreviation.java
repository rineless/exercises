package model.group;

public enum Abbreviation {
    DS("Discrete structures"), ALG("Algorithms"), ANL("Analysis");

     private final String fullName;

    Abbreviation(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}

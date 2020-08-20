package model.group;

public enum Abbreviation {
    DS("Discrete structures"), Alg("Algorithms"), Anl("Analysis");

     private final String fullName;

    Abbreviation(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}

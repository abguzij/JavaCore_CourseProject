package entity.user;

public enum UserType {
    PERSON("Человек"),
    ORGANISATION("Организация");
    private String stringRepresentation;
    UserType(String stringRepresentation){
        this.stringRepresentation = stringRepresentation;
    }



}

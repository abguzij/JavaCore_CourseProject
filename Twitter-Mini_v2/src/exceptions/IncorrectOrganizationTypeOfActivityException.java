package exceptions;

public class IncorrectOrganizationTypeOfActivityException extends Exception{
    public IncorrectOrganizationTypeOfActivityException(String message) {
        super("Возникла ошибка при вводе рода деятельности организации!\n[ОШИБКА]: " + message);
    }
}

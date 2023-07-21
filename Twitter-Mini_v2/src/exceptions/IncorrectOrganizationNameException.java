package exceptions;

public class IncorrectOrganizationNameException extends Exception {
    public IncorrectOrganizationNameException(String message) {
        super("Возникла ошибка при вводе наименования организации!\n[ОШИБКА]: " + message);
    }
}

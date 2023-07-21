package exceptions;

public class IncorrectDateIFormatInputException extends Exception{
    public IncorrectDateIFormatInputException(String message) {
        super("Возникла ошибка при вводе даты!\n[ОШИБКА]: " + message);
    }
}

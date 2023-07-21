package exceptions;

public class IncorrectDateInputException extends Exception{
    public IncorrectDateInputException(String message) {
        super("Возникла ошибка при вводе даты!\n[ОШИБКА]: " + message);
    }
}

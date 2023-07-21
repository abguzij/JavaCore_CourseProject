package exceptions;

public class IncorrectPersonNameException extends Exception{
    public IncorrectPersonNameException(String message) {
        super("Возникла ошибка при вводе имени пользователя!\n[ОШИБКА]: " + message);
    }
}

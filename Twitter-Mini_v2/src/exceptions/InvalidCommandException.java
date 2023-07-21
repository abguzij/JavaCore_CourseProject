package exceptions;

public class InvalidCommandException extends Exception{
    public InvalidCommandException(String message) {
        super("Возникла ошибка при вводе команды!\n[ОШИБКА]: " + message);
    }
}

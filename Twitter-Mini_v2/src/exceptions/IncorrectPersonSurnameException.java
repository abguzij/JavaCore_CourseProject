package exceptions;

public class IncorrectPersonSurnameException extends Exception {
    public IncorrectPersonSurnameException(String message) {
        super("Возникла ошибка при вводе фамилии пользователя!\n[ОШИБКА]: " + message);
    }
}

package exceptions;

public class UserTypeInputException extends Exception{
    private static final String message = "Тип пользователя может быть задан только 0 и 1";
    public UserTypeInputException() {
        super("Возникла ошибка при вводе типа пользователя!\n[ОШИБКА]: " + message);
    }
}

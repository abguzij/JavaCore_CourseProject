package exceptions;

// RuntimeException для того, чтобы их можно было кидать при работе со Stream API
public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String message) {
        super("[ЛОГИН: ОШИБКА]: " + message);
    }
}

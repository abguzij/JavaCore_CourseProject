package exceptions;

// При написании exception нужно учитывать то, как они будут обрабатываться

// RuntimeException для того, чтобы их можно было кидать при работе со Stream API
public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super("Возникла ошибка вводе пароля!\n[ОШИБКА]: " + message);
    }
}

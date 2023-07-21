package exceptions;

public class IncorrectPublicationThemeException extends Exception{
    public IncorrectPublicationThemeException(String message) {
        super("Возникла ошибка при вводе темы публикации!\n[ОШИБКА]: " + message);
    }
}

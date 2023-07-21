package exceptions;

public class IncorrectPublicationTextException extends Exception{
    public IncorrectPublicationTextException(String message) {
        super("Возникла ошибка при вводе текста публикации!\n[ОШИБКА]: " + message);
    }
}

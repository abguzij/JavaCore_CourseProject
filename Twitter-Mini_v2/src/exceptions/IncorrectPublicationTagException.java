package exceptions;

public class IncorrectPublicationTagException extends RuntimeException{
    public IncorrectPublicationTagException(String message) {
        super("Возникла ошибка при вводе тегов публикации!\n[ОШИБКА]: " + message);
    }
}

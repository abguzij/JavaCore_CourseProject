package exceptions;

public class PublicationSearchException extends Exception {
    public PublicationSearchException(String message) {
        super("Возникла ошибка при поиске публикации!\n[ОШИБКА]: " + message);
    }
}

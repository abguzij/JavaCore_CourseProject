package exceptions;

public class FileException extends Exception {
    public FileException(String message) {
        super("Возникла ошибка при работе с файлом!\n[ОШИБКА]: " + message);
    }
}

package example.application.note.exceptions;

public class NotValidDataException extends RuntimeException {

    public NotValidDataException(String message) {
        super(message);
    }
}

package example.application.note.exceptions;

public class TooLongContentException extends NotValidDataException {

    public TooLongContentException() {
        super("Too long content; title must have max 65 536 characters long");
    }
}

package example.application.note.exceptions;

public class TooLongTitleException extends NotValidDataException {

    public TooLongTitleException() {
        super("Too long title; title must have max 256 characters long");
    }
}

package example.application.note.exceptions;

public class NoNotesException extends RuntimeException {

    public NoNotesException() {
        super("There are no notes to be displayed");
    }
}

package example.application.note.exceptions;

public class EmptyNoteException extends NotValidDataException {

    public EmptyNoteException() {
        super("Note title and note content must not be empty");
    }
}

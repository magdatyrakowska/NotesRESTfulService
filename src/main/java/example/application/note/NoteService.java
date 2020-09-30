package example.application.note;

import java.util.List;

public interface NoteService {

    List<Note> getAllNotes();

    Note getNote(Long id);

    void addNote(Note note);

    void updateNote(Long id, Note note);

    void deleteNote(Long id);

}

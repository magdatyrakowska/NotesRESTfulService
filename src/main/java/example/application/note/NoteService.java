package example.application.note;

import java.util.List;
import java.util.Optional;

/**
 * Manages all operations on the Notes repository
 */
public interface NoteService {

    /**
     * Returns list with all notes from repository
     * @return List containing Note objects
     */
    List<Note> getAllNotes();

    /**
     * Returns one not with given id
     * @param id Long number with id of requested note
     * @return Note object from repository of a given Id
     */
    Note getNote(Long id);

    /**
     * Adds given note to a repository
     * @param note Note object to be added
     *             TODO: return
     */
    Note addNote(Note note);

    /**
     * Updates note of a given Id number with parameters from given Note object
     * @param id Long number identifying Note to be updated
     * @param note Note object containing changes to update
     */
    Note updateNote(Long id, Note note);

    /**
     * Deletes note of a given Id number from repository
     * @param id Long number identifying Note to be deleted
     */
    void deleteNote(Long id);

}

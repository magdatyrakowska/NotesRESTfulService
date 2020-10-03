package example.application.note;

import java.util.List;

/**
 * Manages operations on the Notes repository.
 * In results includes only active notes (not archived)
 */
public interface NoteService {

    /**
     * Returns list with all notes from repository
     *
     * @return List containing Note objects
     */
    List<Note> getAllNotes();

    /**
     * Returns one note with given id
     *
     * @param id Long number with id of requested note
     * @return Note object from repository of a given id
     */
    Note getNote(Long id);

    /**
     * Adds given note to a repository
     *
     * @param note Note object to be added
     * @return Note object that was successfully added
     */
    Note addNote(Note note);

    /**
     * Updates note of a given Id number with parameters from given Note object
     *
     * @param id   Long number identifying Note to be updated
     * @param note Note object containing changes to update
     * @return Note object that was successfully updated
     */
    Note updateNote(Long id, Note note);

    /**
     * Deletes note of a given Id number from repository
     *
     * @param id Long number identifying Note to be deleted
     */
    void deleteNote(Long id);

}

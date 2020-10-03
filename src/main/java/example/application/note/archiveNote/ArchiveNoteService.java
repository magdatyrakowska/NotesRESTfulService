package example.application.note.archiveNote;

import example.application.note.Note;

import java.util.List;

/**
 * Manages read operations on the Notes repository,
 * working on all notes (including not active ones).
 */
public interface ArchiveNoteService {

    /**
     * Returns list with all notes from repository, including all their changes.
     * Each change is another Note object, not active, identified by original Id number.
     * @return List containing Note objects
     */
    List<Note> getAllNotes();


    /**
     * Returns list with all changes of a given Id note. Each change is another Note object,
     * not active, identified by original Id number.
     * @param originalId Long number with id of requested note
     * @return List containing Note objects (all active and not active, archived versions of given note)
     */
    List<Note> getNotesByOriginalId(Long originalId);
}

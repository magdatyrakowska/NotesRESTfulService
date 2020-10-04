package example.application.note.archiveNote;

import example.application.note.Note;
import example.application.note.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller managing R (read) operations in repository,
 * with access to history of changes of all notes.
 */
@RestController
public class ArchiveNoteController {

    private ArchiveNoteService archiveNoteService;
    private ArchiveNoteModelAssembler archiveNoteModelAssembler;

    @Autowired
    public ArchiveNoteController(ArchiveNoteService archiveNoteService, ArchiveNoteModelAssembler archiveNoteModelAssembler) {
        this.archiveNoteService = archiveNoteService;
        this.archiveNoteModelAssembler = archiveNoteModelAssembler;
    }

    /**
     * Returns list with history of all notes
     * as a list of archived notes.
     *
     * @return ResponseEntity with List of Notes and HttpStatus Ok.
     */
    @GetMapping("/archive")
    public ResponseEntity<Object> listNotes() {
        List<Note> notes = archiveNoteService.getAllNotes();
        return new ResponseEntity<>(
                archiveNoteModelAssembler.toCollectionModel(notes),
                HttpStatus.OK);
    }

    /**
     * Returns history of note changes as a list of archived notes.
     *
     * @param originalId Long number identifying requested note
     * @return ResponseEntity with List of Notes matching given Id and HttpStatus Ok;
     * In case there are no notes with that originalId it return HttpStatus not found.
     */
    @GetMapping("/archive/{originalId}")
    public ResponseEntity<Object> getNoteById(@PathVariable Long originalId) {
        List<Note> notes;
        try {
            notes = archiveNoteService.getNotesByOriginalId(originalId);
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(
                archiveNoteModelAssembler.toCollectionModel(notes),
                HttpStatus.OK);
    }
}

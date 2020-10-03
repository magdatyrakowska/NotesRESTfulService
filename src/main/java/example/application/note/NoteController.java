package example.application.note;

import example.application.note.exceptions.NotValidDataException;
import example.application.note.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller managing all CRUD endpoints
 */
@RestController
public class NoteController {

    private NoteService noteService;
    private NoteModelAssembler noteModelAssembler;

    @Autowired
    public NoteController(NoteServiceImplementation noteService, NoteModelAssembler noteModelAssembler) {
        this.noteService = noteService;
        this.noteModelAssembler = noteModelAssembler;
    }

    /**
     * Returns list of all notes.
     *
     * @return ResponseEntity with List of Notes and HttpStatus Ok.
     */
    @GetMapping("/notes")
    public ResponseEntity<Object> listNotes() {
        List<Note> notes = noteService.getAllNotes();
        return new ResponseEntity<>(
                noteModelAssembler.toCollectionModel(notes),
                HttpStatus.OK);
    }

    /**
     * Returns specific one note or error status in case there is no such note.
     *
     * @param id Long number identifying requested note
     * @return ResponseEntity with Note object matching given Id or error status
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<Object> getNoteById(@PathVariable Long id) {
        Note note;
        try {
            note = noteService.getNote(id);
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(noteModelAssembler.toModel(note), HttpStatus.OK);
    }

    /**
     * Adds given note to a repository.
     * Error is send when note data is not valid.
     *
     * @param note Note object to be added
     * @return ResponseEntity with added note in case of success or error description
     */
    @PostMapping("/notes")
    public ResponseEntity<Object> addNote(@RequestBody Note note) {
        try {
            note.checkValid();
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        Note savedNote = noteService.addNote(note);
        return new ResponseEntity<>(noteModelAssembler.toModel(savedNote), HttpStatus.CREATED);
    }

    /**
     * Updates note of a given Id in repository or an error message.
     * Error is send when there is no such note in repository or changes to be updated
     * are not valid.
     *
     * @param note Note object with changes to update
     * @param id Long number identifying note to be updated or error message
     * @return ResponseEntity with updated note in case of success or error description
     */
    @PutMapping("/notes/{id}")
    public ResponseEntity<Object> updateNote(@RequestBody Note note, @PathVariable Long id) {
        Note updatedNote;
        try {
            updatedNote = noteService.updateNote(id, note);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(noteModelAssembler.toModel(updatedNote), HttpStatus.OK);
    }

    /**
     * Deletes note of a given Id in repository if such note exist.
     * Otherwise send error message.
     *
     * @param id Long number identifying note to be deleted
     * @return ResponseEntity with HttpStatus no content or error description
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Object> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

}

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
     * @return List with Note objects.
     */
    @GetMapping("/notes")
    public ResponseEntity<Object> listNotes() {
        List<Note> notes = noteService.getAllNotes();
        return new ResponseEntity<>(
                noteModelAssembler.toCollectionModel(notes),
                HttpStatus.OK
        );
    }

    /**
     * Returns specific one note or error status in case there is no such note.
     *
     * @param id Long number identifying requested note
     * @return Note object matching given Id or error status
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<Object> getNoteById(@PathVariable Long id) {
        try {
            Note note = noteService.getNote(id);
            return new ResponseEntity<>(noteModelAssembler.toModel(note), HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adds given note to a repository.
     * Error is send when note data is not valid.
     *
     * @param note Note object to be added
     * @return message with success or error description
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
     * @param id   Long number identifying note to be updated or error message
     * @return message with success or error description
     * // TODO return
     */
    @PutMapping("/notes/{id}")
    public ResponseEntity<Object> updateNote(@RequestBody Note note, @PathVariable Long id) {
        try {
            Note updatedNote = noteService.updateNote(id, note);
            return new ResponseEntity<>(noteModelAssembler.toModel(updatedNote), HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Deletes note of a given Id in repository if such note exist.
     * Otherwise send error message.
     *
     * @param id Long number identifying note to be deleted
     * @return message with success or error description
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Object> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            String message = "Note " + id + " successfully deleted";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

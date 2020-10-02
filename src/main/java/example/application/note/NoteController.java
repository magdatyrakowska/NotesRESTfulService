package example.application.note;

import example.application.note.exceptions.NoNotesException;
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

    @Autowired
    public NoteController(NoteServiceImplementation noteService) {
        this.noteService = noteService;
    }

    /**
     * Returns list of all notes or error message in case there are no notes in repository.
     *
     * @return List with Note objects or error message
     */
    @GetMapping("/notes")
    public ResponseEntity<Object> listNotes() {
        try {
            List<Note> notes = noteService.getAllNotes();
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } catch (NoNotesException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns specific one note or error message in case there is no such note.
     *
     * @param id Long number identifying requested note
     * @return Note object matching given Id or error message
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<Object> getNoteById(@PathVariable Long id) {
        try {
            Note note = noteService.getNote(id);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
        String message = "Note added successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Updates note of a given Id in repository or an error message.
     * Error is send when there is no such note in repository or changes to be updated
     * are not valid.
     *
     * @param note Note object with changes to update
     * @param id   Long number identifying note to be updated or error message
     * @return message with success or error description
     */
    @PutMapping("/notes/{id}")
    public ResponseEntity<Object> updateNote(@RequestBody Note note, @PathVariable Long id) {
        try {
            noteService.updateNote(id, note);
            String message = "Note " + id + " successfully updated";
            return new ResponseEntity<>(message, HttpStatus.OK);
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
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            String message = "Note " + id + " successfully deleted";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

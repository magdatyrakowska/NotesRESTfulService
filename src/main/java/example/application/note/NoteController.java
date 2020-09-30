package example.application.note;

import org.springframework.beans.factory.annotation.Autowired;
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
     * Returns list of all notes
     * @return List with Note objects
     */
    @GetMapping("/notes")
    public List<Note> listNotes() {
        return noteService.getAllNotes();
    }

    /**
     * Returns specific one note
     * @param id Long number identifying requested note
     * @return Note object matching given Id
     */
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNote(id);
    }

    /**
     * Adds given note to a repository
     * @param note Note object to be added
     */
    @PostMapping("/notes")
    public void addNote(@RequestBody Note note) {
        noteService.addNote(note);
    }

    /**
     * Updates note of a given Id in repository
     * @param note Note object with changes to add
     * @param id Long number identifying note to be updated
     */
    @PutMapping("/notes/{id}")
    public void updateNote(@RequestBody Note note, @PathVariable Long id) {
        noteService.updateNote(id, note);
    }

    /**
     * Deletes note of a given Id in repository
     * @param id Long number identifying note to be deleted
     */
    @DeleteMapping("/notes/{id}")
    public void deleteTopic(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

}

package example.application.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public List<Note> listNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNote(id);
    }

    @PostMapping("/notes")
    public void addNote(@RequestBody Note note) {
        noteService.addNote(note);
    }

    @PutMapping("/notes/{id}")
    public void updateNote(@RequestBody Note note, @PathVariable Long id) {
        noteService.updateNote(id, note);
    }

    @DeleteMapping("/notes/{id}")
    public void deleteTopic(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

}

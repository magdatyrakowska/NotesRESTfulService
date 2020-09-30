package example.application.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of service interface
 */
@Service
public class NoteServiceImplementation implements NoteService{

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImplementation(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        noteRepository.findAll().forEach(notes::add);
        return notes;
    }

    @Override
    public Note getNote(Long id) {
        Note note = noteRepository.findById(id).orElse(null);
        return note;
    }

    @Override
    public void addNote(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void updateNote(Long id, Note note) {
        Note updatedNote = noteRepository.findById(id).get();
        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());

        noteRepository.save(updatedNote);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}

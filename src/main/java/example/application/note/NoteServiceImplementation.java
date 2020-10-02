package example.application.note;

import example.application.note.exceptions.NotValidDataException;
import example.application.note.exceptions.NoteNotFoundException;
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
        noteRepository.findAll()
                .forEach(notes::add);
        return notes;
    }

    @Override
    public Note getNote(Long id) throws NoteNotFoundException {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        return note;
    }

    @Override
    public Note addNote(Note note) throws NotValidDataException {
        note.checkValid();
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    @Override
    public Note updateNote(Long id, Note note) throws NoteNotFoundException, NotValidDataException {
        Note updatedNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        note.checkValid();

        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());

        Note savedNote = noteRepository.save(updatedNote);
        return savedNote;
    }

    @Override
    public void deleteNote(Long id) throws NoteNotFoundException {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new NoteNotFoundException(id);
        }
    }
}

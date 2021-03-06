package example.application.note;

import example.application.note.exceptions.NotValidDataException;
import example.application.note.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of NoteService interface
 */
@Service
public class NoteServiceImplementation implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImplementation(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        noteRepository.findByActive(true).forEach(notes::add);

        return notes.stream()
                .collect(Collectors.toList());
    }

    @Override
    public Note getNote(Long id) throws NoteNotFoundException {
        Note note = noteRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> new NoteNotFoundException(id));
        return note;
    }

    @Override
    public Note addNote(Note note) throws NotValidDataException {
        note.checkValid();
        Note savedNote = noteRepository.save(note);
        savedNote = setOriginalId(savedNote);

        return savedNote;
    }


    @Override
    public Note updateNote(Long id, Note note) throws NoteNotFoundException, NotValidDataException {
        Note updatedNote;
        updatedNote = noteRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> new NoteNotFoundException(id));
        note.checkValid();

        Note archivedNote = new Note(updatedNote);
        noteRepository.save(archivedNote);

        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());
        updatedNote.setModifiedToNow();
        updatedNote.increaseVersion();

        Note savedNote = noteRepository.save(updatedNote);
        return savedNote;
    }

    @Override
    public void deleteNote(Long id) throws NoteNotFoundException {
        Note deletedNote = noteRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> new NoteNotFoundException(id));
        deletedNote.setActive(false);
        noteRepository.save(deletedNote);
    }

    private Note setOriginalId(Note savedNote) {
        savedNote = noteRepository.findById(savedNote.getId()).get();
        savedNote.setOriginalId(savedNote.getId());
        savedNote = noteRepository.save(savedNote);
        return savedNote;
    }
}

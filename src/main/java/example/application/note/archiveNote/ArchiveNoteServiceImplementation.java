package example.application.note.archiveNote;

import example.application.note.Note;
import example.application.note.NoteRepository;
import example.application.note.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ArchiveNoteService interface
 */
@Service
public class ArchiveNoteServiceImplementation implements ArchiveNoteService {

    private NoteRepository noteRepository;

    @Autowired
    public ArchiveNoteServiceImplementation(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        noteRepository.findAll().forEach(notes::add);

        return notes.stream()
                .sorted((n1, n2) -> (int) (n1.getOriginalId() - n2.getOriginalId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Note> getNotesByOriginalId(Long originalId) throws NoteNotFoundException {
        List<Note> notes = new ArrayList<>();
        noteRepository.findByOriginalId(originalId).forEach(notes::add);

        if (notes.size() == 0) {
            throw new NoteNotFoundException(originalId);
        }

        return notes;
    }

}

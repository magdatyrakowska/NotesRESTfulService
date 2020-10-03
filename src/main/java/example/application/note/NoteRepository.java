package example.application.note;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findByActive(Boolean active);

    Optional<Note> findByIdAndActive(Long id, Boolean active);

    List<Note> findByOriginalId(Long originalId);
}

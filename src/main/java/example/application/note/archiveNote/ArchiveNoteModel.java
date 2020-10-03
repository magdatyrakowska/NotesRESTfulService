package example.application.note.archiveNote;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

/**
 * Model of archive note used to display data in HATEOAS style.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "archive notes")
public class ArchiveNoteModel extends RepresentationModel<ArchiveNoteModel> {

    private Long id;
    private Long version;
    private Boolean active;
    private String title;
    private String content;
    private LocalDate created;
    private LocalDate modified;

}

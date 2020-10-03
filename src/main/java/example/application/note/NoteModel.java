package example.application.note;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "notes")
public class NoteModel extends RepresentationModel<NoteModel> {

    private Long id;
    private String title;
    private String content;
    private LocalDate created;
    private LocalDate modified;
}

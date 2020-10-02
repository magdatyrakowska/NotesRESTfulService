package example.application.note;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "notes")
public class NoteModel extends RepresentationModel<NoteModel> {

    private Long id;
    private String title;
    private String content;
}

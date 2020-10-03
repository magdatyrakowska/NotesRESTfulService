package example.application.note.archiveNote;

import example.application.note.Note;
import example.application.note.NoteController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembler converting Notes objects to ArchiveNoteModel with links.
 */
@Component
public class ArchiveNoteModelAssembler extends RepresentationModelAssemblerSupport<Note, ArchiveNoteModel> {

    public ArchiveNoteModelAssembler() {
        super(ArchiveNoteController.class, ArchiveNoteModel.class);
    }

    @Override
    public ArchiveNoteModel toModel(Note note) {

        ArchiveNoteModel archiveNoteModel = instantiateModel(note);
        archiveNoteModel.add(linkTo(
                methodOn(ArchiveNoteController.class).getNoteById(note.getOriginalId()))
                .withSelfRel());

        archiveNoteModel.setId(note.getOriginalId());
        archiveNoteModel.setVersion((note.getVersion()));
        archiveNoteModel.setActive(note.getActive());
        archiveNoteModel.setTitle(note.getTitle());
        archiveNoteModel.setContent(note.getContent());
        archiveNoteModel.setCreated(note.getCreated());
        archiveNoteModel.setModified(note.getModified());

        return archiveNoteModel;
    }


    @Override
    public CollectionModel<ArchiveNoteModel> toCollectionModel(Iterable<? extends Note> notes) {
        CollectionModel<ArchiveNoteModel> archiveNoteModels = super.toCollectionModel(notes);
        archiveNoteModels.add(WebMvcLinkBuilder.linkTo(
                methodOn(NoteController.class).listNotes())
                .withRel("all archived notes"));

        return archiveNoteModels;
    }
}

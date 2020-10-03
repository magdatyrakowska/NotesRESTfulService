package example.application.note;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NoteModelAssembler extends RepresentationModelAssemblerSupport<Note, NoteModel> {

    public NoteModelAssembler() {
        super(NoteController.class, NoteModel.class);
    }

    @Override
    public NoteModel toModel(Note note) {

        NoteModel noteModel = instantiateModel(note);
        noteModel.add(linkTo(
                methodOn(NoteController.class).getNoteById(note.getId()))
                .withSelfRel());

        noteModel.setId(note.getId());
        noteModel.setTitle(note.getTitle());
        noteModel.setContent(note.getContent());
        noteModel.setCreated(note.getCreated());
        noteModel.setModified(note.getModified());

        return noteModel;
    }

    @Override
    public CollectionModel<NoteModel> toCollectionModel(Iterable<? extends Note> entities) {
        CollectionModel<NoteModel> noteModels = super.toCollectionModel(entities);
        noteModels.add(linkTo(
                methodOn(NoteController.class).listNotes())
                .withRel("all notes"));

        return noteModels;
    }
}

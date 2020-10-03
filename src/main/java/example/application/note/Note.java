package example.application.note;

import example.application.note.exceptions.EmptyNoteException;
import example.application.note.exceptions.NotValidDataException;
import example.application.note.exceptions.TooLongContentException;
import example.application.note.exceptions.TooLongTitleException;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    @Lob
    private String content;
    private LocalDate created;
    private LocalDate modified;
    private Long version;
    private Long originalId;
    private Boolean active;

    public Note() {
        this.created = LocalDate.now();
        this.modified = LocalDate.now();
        this.version = 0l;
        this.active = true;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.created = LocalDate.now();
        this.modified = LocalDate.now();
        this.version = 0l;
        this.active = true;
    }

    public Note(Note note) {
        this.title = note.getTitle();
        this.content = note.getContent();
        this.created = note.getCreated();
        this.modified = note.getModified();
        this.version = note.getVersion();
        this.active = false;
        this.originalId = note.getId();
    }

    public void checkValid() throws NotValidDataException {
        if (title.isBlank() || content.isBlank()) {
            throw new EmptyNoteException();
        } else if (title.length() > 256) {
            throw new TooLongTitleException();
        } else if (content.length() > Integer.MAX_VALUE) {
            throw new TooLongContentException();
        }
    }

    public void increaseVersion() {
        this.version++;
    }

    public void setModifiedToNow() {
        this.modified = LocalDate.now();
    }
}

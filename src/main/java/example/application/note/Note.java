package example.application.note;

import example.application.note.exceptions.EmptyNoteException;
import example.application.note.exceptions.NotValidDataException;
import example.application.note.exceptions.TooLongContentException;
import example.application.note.exceptions.TooLongTitleException;

import javax.persistence.*;

@Entity
public class Note {

    @Id @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    @Lob
    private String content;
    //private LocalDate created;
    //private LocalDate modified;
    //private Long version;

    public Note() {
    }

    public Note( String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void checkValid() throws NotValidDataException {
        if (title.isBlank() || content.isBlank()) {
            throw new EmptyNoteException();
        } else if (title.length() > 256) {
            throw new TooLongTitleException();
        } else if (content.length() > 65536) {
            throw new TooLongContentException();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

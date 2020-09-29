package example.application.note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Note {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;
    //private LocalDate created;
    //private LocalDate modified;
    //private Long version;

    public Note() {
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }


}

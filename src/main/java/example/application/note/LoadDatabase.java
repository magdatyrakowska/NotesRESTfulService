package example.application.note;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(NoteRepository noteRepository) {
        return args -> {
            noteRepository.save(new Note( "title1", "content 1"));
            noteRepository.save(new Note( "title2", "content 2"));
        };
    }
}

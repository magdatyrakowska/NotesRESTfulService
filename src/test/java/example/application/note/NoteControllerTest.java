package example.application.note;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {


    private final MockMvc mockMvc;

    @Autowired
    public NoteControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void ListAllNotesWhenRepoIsEmptyGivesStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void GetNotExistingNoteGivesStatusNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notes/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void PutNonExistingNoteGivesStatusNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/notes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"new title\", \"content\": \"new content\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void DeleteNonExistingNoteGivesStatusNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


}

package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestUtil testUtil;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void resetDb() {
        commentRepository.deleteAll();
        adRepository.deleteAll();
        userRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    @DisplayName("Получение списка комментариев объявления")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnCollectionOfComments() throws Exception {

        Comment comment = testUtil.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}/comments", comment.getAd().getPk())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].author").value(comment.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorImage").value("/users/image/" +
                        comment.getAuthor().getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorFirstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].createdAt", Matchers.lessThan(System.currentTimeMillis())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].text").value("TextComment"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление комментария к объявлению")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnComment() throws Exception {

        CreateOrUpdateCommentDto createOrUpdateCommentDto = new CreateOrUpdateCommentDto();
        createOrUpdateCommentDto.setText("SomeCommentText");

        Ad ad = testUtil.createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.post("/ads/{id}/comments", ad.getPk())
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(ad.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorImage").value("/users/image/"
                        + ad.getAuthor().getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.lessThan(System.currentTimeMillis())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("SomeCommentText"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление комментария по его id")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk() throws Exception {

        Comment comment = testUtil.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{adId}/comments/{commentId}",
                        comment.getAd().getPk(),
                        comment.getPk()))
                .andExpect(status().isOk());

        assertFalse(commentRepository.findById(comment.getPk()).isPresent());
    }

    @Test
    @DisplayName("Обновление комментария")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnUpdateComment() throws Exception {

        CreateOrUpdateCommentDto createOrUpdateCommentDto = new CreateOrUpdateCommentDto();
        createOrUpdateCommentDto.setText("UpdateComment");

        Comment comment = testUtil.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{adId}/comments/{commentId}",
                                comment.getAd().getPk(),
                                comment.getPk())
                        .content(objectMapper.writeValueAsString(createOrUpdateCommentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(comment.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorImage").value("/users/image/"
                        + comment.getAuthor().getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.lessThan(System.currentTimeMillis())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("UpdateComment"))
                .andExpect(status().isOk());
    }
}
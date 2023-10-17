package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.courseWork.dto.CreateOrUpdateCommentDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Comment;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.*;
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




    @Test
    @DisplayName("Получение комментария")
    void shouldReturnNewComment() throws Exception {

        Image image = new Image();
        image.setId(1);
        image.setData("picture".getBytes());

        User user = new User();

        user.setId(1);
        user.setEmail("user@gmail.com");
        user.setPassword("$2a$12$QQrSELP5IwYp8TRE4m1tgu1972.T7nlvoDOphkuY0EQ5pehZIADTq");
        user.setFirstName("Ura");
        user.setLastName("Brylev");
        user.setPhone("+79210000000");
        user.setRole(Role.USER);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setPk(1);
        ad.setAuthor(user);
        ad.setImage(image);
        ad.setPrice(1234);
        ad.setDescription("description");
        ad.setTitle("title");

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setAd(ad);
        comment.setCreateAt(System.currentTimeMillis());
        comment.setText("NewCommentText");

        commentRepository.save(comment);


        CreateOrUpdateCommentDto createOrUpdateCommentDto = new CreateOrUpdateCommentDto();
        createOrUpdateCommentDto.setText("NewCommentText");

        mockMvc.perform(MockMvcRequestBuilders.get("/{id}/comments", comment.getAd().getPk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("NewCommentText"))
                .andExpect(status().isOk());

    }

}
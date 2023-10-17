package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.courseWork.dto.AdDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private MockMvc mockMvc;


    public Image createImage() {
        Image image = new Image();
        image.setData("image".getBytes());
        image.setId(1);
        return image;
    }

    public User createUser() {


        User user = new User();

        user.setId(1);
        user.setEmail("user@gmail.com");
        user.setPassword("$2a$12$QQrSELP5IwYp8TRE4m1tgu1972.T7nlvoDOphkuY0EQ5pehZIADTq");
        user.setFirstName("Ura");
        user.setLastName("Brylev");
        user.setPhone("+79210000000");
        user.setRole(Role.USER);
        return user;
    }

    public Ad createAd() {

        Ad ad = new Ad();
        ad.setPk(1);
        ad.setPrice(1234);
        ad.setDescription("description");
        ad.setTitle("title");

        return ad;
    }


    /*@AfterEach
    public void resetDb() {
        adRepository.deleteAll();
    }*/

    @Test
    @DisplayName("Добавление объявления")
    void shouldReturnAdWhenAddAdCalled() throws Exception {

        Image image = createImage();

        User user = createUser();
        user.setImage(image);

        Ad ad = createAd();
        ad.setAuthor(user);
        ad.setImage(image);


        mockMvc.perform(MockMvcRequestBuilders.post("/ads")
                        .content(objectMapper.writeValueAsString(ad))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pk").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title"))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Удаление объявления")
    void shouldReturnOkWhenDeleteAdCalled() throws Exception {

        Image image = createImage();
        imageRepository.save(image);
        User user = createUser();
        user.setImage(image);

        userRepository.save(user);


        Ad ad = createAd();
        ad.setImage(image);
        ad.setAuthor(user);
        adRepository.save(ad);
        mockMvc.perform((MockMvcRequestBuilders.delete("/{id}", ad.getPk())))
                .andExpect(status().isOk());
    }
}
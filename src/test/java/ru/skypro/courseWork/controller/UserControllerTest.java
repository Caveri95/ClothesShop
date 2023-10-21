package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.courseWork.dto.NewPasswordDto;
import ru.skypro.courseWork.dto.UpdateUserDto;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestUtil testUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    @DisplayName("Обновление пароля")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk_WhenPasswordUpdateCalled() throws Exception {

        User user = testUtil.createTestUser();

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword("password"); //тут текущий пароль незакодированный
        newPasswordDto.setNewPassword("newPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                        .content(objectMapper.writeValueAsString(newPasswordDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        assertTrue(passwordEncoder.matches("newPassword", userRepository.findByEmail(user.getEmail()).get().getPassword()));
    }


    @Test
    @DisplayName("Получение информации об авторизованном пользователе")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnInfoAboutAuthenticatedUser_WhenGetMyInfoCalled() throws Exception {

        User user = testUtil.createTestUser();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79000000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(user.getRole().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/users/image/"
                        + user.getImage().getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление информации об авторизованном пользователе")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnUpdateInfoAboutUser_WhenUpdateMyInfoCalled() throws Exception {

        testUtil.createTestUser();

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("UpdateName");
        updateUserDto.setLastName("UpdateLast");
        updateUserDto.setPhone("+79999999999");

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                        .content(objectMapper.writeValueAsString(updateUserDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("UpdateName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("UpdateLast"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79999999999"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление аватара авторизованного пользователя")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk_WhenUpdateAvatarCalled() throws Exception {

        User user = testUtil.createTestUser();

        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "someImage!".getBytes()
        );

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/users/me/image");

        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        assertArrayEquals(image.getBytes(), userRepository.findById(user.getId()).get().getImage().getData());
    }

    @Test
    @DisplayName("Получение изображения пользователя по его id")
    @WithMockUser
    void shouldReturnArrayOfByteImage_WhenGetImageCalled() throws Exception {

        User user = testUtil.createTestUser();

        mockMvc.perform(get("/users/image/{id}", user.getImage().getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("userImage"))
                .andExpect(status().isOk());
    }
}

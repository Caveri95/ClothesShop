package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.dto.Role;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.Image;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //БД заполняется и id меняется, даже после очистки репозитория
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
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void resetDb() {
        adRepository.deleteAll();
        userRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    @DisplayName("Добавление объявления")
    @Order(1)
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnAdWhenAddAdCalled() throws Exception {

        User user = creatTestUser();

        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto(
                "titletitle",
                1234,
                "description");

        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "someImage!".getBytes()
        );

        MockMultipartFile request
                = new MockMultipartFile(
                "properties",
                "properties",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(createOrUpdateAdDto).getBytes()
        );

        mockMvc.perform(multipart("/ads")
                        .file(request)
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/1")) //Здесь не факт, что 1 будет
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение информации об объявлении")
    @Order(2)
    @WithMockUser
    void shouldReturnAdInfo() throws Exception {

        Ad ad = createTestAd();

        mockMvc.perform(get("/ads/{id}", ad.getPk())
                        .content(objectMapper.writeValueAsString(ad))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorLastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/" + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79000000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление объявления")
    @Order(3)
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOkWhenDeleteAdCalled() throws Exception {

        Ad ad = createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{id}", ad.getPk()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение всех объявлений")
    @Order(4)
    @WithMockUser
    void shouldReturnCollectionOfAds() throws Exception {

        createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление информации об объявлении")
    @Order(5)
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnUpdateInformationAboutAd() throws Exception {

        Ad ad = createTestAd();

        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto(
                "UpdateTitle",
                9999,
                "UpdateDescription");

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", ad.getPk())
                        .content(objectMapper.writeValueAsString(createOrUpdateAdDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(ad.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/" + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(9999))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UpdateTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение объявлений авторизованного пользователя")
    @Order(6)
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnCollectionOfMyAds() throws Exception {

        createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление картинки объявления")
    @Order(7)
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk() throws Exception {

        Ad ad = createTestAd();

        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "someImage!".getBytes()
        );

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/ads/{id}/image", ad.getPk());

        builder.with(request -> {
            request.setMethod("PATCH"); //потому что с patch не хочет работать
            return request;
        });

        mockMvc.perform(builder
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение изображения объявления")
    @Order(8)
    @WithMockUser
    void shouldReturnArrayOfByteImage() throws Exception {

        Ad ad = createTestAd();

        mockMvc.perform(get("/ads/image/{id}", ad.getImage().getId())
                        .content(objectMapper.writeValueAsString(ad))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("adImage"))
                .andExpect(status().isOk());
    }

    private User creatTestUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("$2a$10$j4g2V3qsGCRffvV/bYpE6uJrhEFFyCSKwRFnSK5QXdX8z88Ao6JEu");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("+79000000000");
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }

    private Ad createTestAd() {

        Image imageForAd = new Image();
        imageForAd.setData("adImage".getBytes());
        imageRepository.save(imageForAd);

        Image imageForUser = new Image();
        imageForUser.setData("userImage".getBytes());
        imageRepository.save(imageForUser);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("$2a$10$j4g2V3qsGCRffvV/bYpE6uJrhEFFyCSKwRFnSK5QXdX8z88Ao6JEu");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("+79000000000");
        user.setRole(Role.USER);
        user.setImage(imageForUser);
        userRepository.save(user);

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setImage(imageForAd);
        ad.setPrice(1234);
        ad.setDescription("description");
        ad.setTitle("titletitle");
        adRepository.save(ad);

        return ad;
    }
}
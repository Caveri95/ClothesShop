package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.courseWork.dto.CreateOrUpdateAdDto;
import ru.skypro.courseWork.entity.Ad;
import ru.skypro.courseWork.entity.User;
import ru.skypro.courseWork.repository.AdRepository;
import ru.skypro.courseWork.repository.ImageRepository;
import ru.skypro.courseWork.repository.UserRepository;
import ru.skypro.courseWork.util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        adRepository.deleteAll();
        userRepository.deleteAll();
        imageRepository.deleteAll();
    }

    @Test
    @DisplayName("Добавление объявления")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnAd_WhenAddAdCalled() throws Exception {

        User user = testUtil.createTestUser();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение информации об объявлении")
    @WithMockUser
    void shouldReturnAdInfo_WhenAdInfoCalled() throws Exception {

        Ad ad = testUtil.createTestAd();
        mockMvc.perform(get("/ads/{id}", ad.getPk())
                        .content(objectMapper.writeValueAsString(ad))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorLastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/"
                        + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79000000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление объявления")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk_WhenDeleteAdCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{id}", ad.getPk()))
                .andExpect(status().isOk());

        assertFalse(adRepository.findById(ad.getPk()).isPresent());
    }

    @Test
    @DisplayName("Получение всех объявлений")
    @WithMockUser
    void shouldReturnCollectionOfAds_WhenGetAllAdsCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].author").value(ad.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].image").value("/ads/image/"
                        + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление информации об объявлении")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnUpdateInformationAboutAd_WhenUpdateAdCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto(
                "UpdateTitle",
                9999,
                "UpdateDescription");

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", ad.getPk())
                        .content(objectMapper.writeValueAsString(createOrUpdateAdDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(ad.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/"
                        + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(9999))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UpdateTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение объявлений авторизованного пользователя")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnCollectionOfMyAds_WhenGetAllMyAdsCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].author").value(ad.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].image").value("/ads/image/"
                        + ad.getImage().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].price").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("titletitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление картинки объявления")
    @WithMockUser(value = "test@gmail.com")
    void shouldReturnOk_WhenUpdateImageAdCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "someImage!".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/ads/{id}/image", ad.getPk())
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        assertTrue(adRepository.findById(ad.getPk()).isPresent());
        Ad updateAd = adRepository.findById(ad.getPk()).get();

        assertTrue(imageRepository.findById(updateAd.getImage().getId()).isPresent());

        assertArrayEquals(image.getBytes(), imageRepository.findById(updateAd.getImage().getId()).get().getData());
    }

    @Test
    @DisplayName("Получение изображения объявления")
    @WithMockUser
    void shouldReturnArrayOfByteImage_WhenGetImageCalled() throws Exception {

        Ad ad = testUtil.createTestAd();

        mockMvc.perform(get("/ads/image/{id}", ad.getImage().getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("adImage"))
                .andExpect(status().isOk());
    }
}
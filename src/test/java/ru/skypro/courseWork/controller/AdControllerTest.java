package ru.skypro.courseWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.courseWork.repository.AdRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AdControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private MockMvc mockMvc;


    /*@AfterEach
    public void resetDb() {
        adRepository.deleteAll();
    }*/

    @Test
    void getAllAds() {
    }
}
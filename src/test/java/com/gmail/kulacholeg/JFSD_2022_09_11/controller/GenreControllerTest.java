package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.kulacholeg.JFSD_2022_09_11.StartApplication;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookDetailsDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = StartApplication.class
)
@AutoConfigureMockMvc
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getGenres() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/genres"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDetailsDto> response = (List<BookDetailsDto>) parseResponse(result, List.class);

        assertThat(response.size()).isEqualTo(3);
    }

    private <T> T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}

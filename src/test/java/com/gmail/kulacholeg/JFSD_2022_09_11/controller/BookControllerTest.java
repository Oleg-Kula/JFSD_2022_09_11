package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.kulacholeg.JFSD_2022_09_11.StartApplication;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.RestResponse;
import com.gmail.kulacholeg.JFSD_2022_09_11.entity.BookEntity;
import com.gmail.kulacholeg.JFSD_2022_09_11.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = StartApplication.class
)
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBook() throws Exception {
        String title = "Test Title";
        String author = "Test Author";
        int genreId = 1;
        String body = """
                {
                    "title": "%s",
                    "author": "%s",
                    "genreId": %d
                }               
                  """.formatted(title, author, genreId);
        MvcResult mvcResult = mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();

        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        int bookId = Integer.parseInt(response.getResult());
        assertThat(bookId).isGreaterThanOrEqualTo(1);

        BookEntity book = bookRepository.findById(bookId).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getGenre().getId()).isEqualTo(genreId);
    }

    @Test
    void getBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void searchBook() {
    }

    private <T>T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}
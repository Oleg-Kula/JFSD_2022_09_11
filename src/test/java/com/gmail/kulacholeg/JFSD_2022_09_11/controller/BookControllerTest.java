package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.kulacholeg.JFSD_2022_09_11.StartApplication;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookDetailsDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        String title = "Test Title 4";
        String author = "Test Author 4";
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
    void getBook() throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/api/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        BookDetailsDto response = parseResponse(mvcResult, BookDetailsDto.class);
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getTitle()).isEqualTo("Test Title 1");
        assertThat(response.getAuthor()).isEqualTo("Test Author 1");
        assertThat(response.getGenre()).isEqualTo("fantastic");
    }

    @Test
    void updateBook() throws Exception{
        String title = "Test Title 5";
        String author = "Test Author 5";
        int genreId = 1;
        String body = """
                {
                    "title": "%s",
                    "author": "%s",
                    "genreId": %d
                }               
                  """.formatted(title, author, genreId);

        MvcResult mvcResult = mvc.perform(put("/api/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDetailsDto response = parseResponse(mvcResult, BookDetailsDto.class);
        assertThat(response.getId()).isEqualTo(2);
        assertThat(response.getTitle()).isEqualTo("Test Title 5");
        assertThat(response.getAuthor()).isEqualTo("Test Author 5");
        assertThat(response.getGenre()).isEqualTo("horror");
    }

    @Test
    void deleteBook() throws Exception{
        String body = """
                {
                    "title": "Delete Book",
                    "author": "Delete Author",
                    "genreId": 3
                }               
                  """;
        MvcResult mvcResult = mvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();
        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        int bookId = Integer.parseInt(response.getResult());
        mvc.perform(MockMvcRequestBuilders.delete("/api/books/" + bookId))
                .andExpect(status().isOk())
                .andReturn();
        mvc.perform(MockMvcRequestBuilders.get("/api/books/" +bookId))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void searchBook() throws Exception{
        String author = "Test Author Search";
        String genre = "detective";
        String body = """
                {
                    "author": "%s",
                    "genre": "%s"
                }               
                  """.formatted(author, genre);
        MvcResult mvcResult = mvc.perform(post("/api/books/_search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<BookDetailsDto> response =(List<BookDetailsDto>) parseResponse(mvcResult, List.class);

        assertThat(response.size()).isEqualTo(3);
    }

    private <T>T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}
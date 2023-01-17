package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookDetailsDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookQueryDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookSaveDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.RestResponse;
import com.gmail.kulacholeg.JFSD_2022_09_11.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createBook(@Valid @RequestBody BookSaveDto dto) {
        return new RestResponse(String.valueOf(bookService.createBook(dto)));
    }

    @GetMapping("/{id}")
    public BookDetailsDto getBook(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public BookDetailsDto updateBook(@Valid @PathVariable int id,
                                     @RequestBody BookSaveDto dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public RestResponse deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return new RestResponse("Deleted");
    }

    @PostMapping("/_search")
    public List<BookDetailsDto> searchBook(@RequestBody(required = false) BookQueryDto query) {
        return bookService.search(query);
    }
}

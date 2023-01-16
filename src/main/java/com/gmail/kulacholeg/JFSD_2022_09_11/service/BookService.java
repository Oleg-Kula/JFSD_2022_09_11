package com.gmail.kulacholeg.JFSD_2022_09_11.service;

import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookDetailsDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookQueryDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookSaveDto;

import java.util.List;

public interface BookService {
    int createBook(BookSaveDto dto);
    BookDetailsDto getBook(int id);
    BookDetailsDto updateBook(int id, BookSaveDto dto);
    List<BookDetailsDto> search(BookQueryDto query);
    void deleteBook(int id);

}

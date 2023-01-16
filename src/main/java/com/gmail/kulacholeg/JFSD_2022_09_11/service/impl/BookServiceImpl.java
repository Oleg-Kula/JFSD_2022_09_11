package com.gmail.kulacholeg.JFSD_2022_09_11.service.impl;

import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookDetailsDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookQueryDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.dto.BookSaveDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.entity.BookEntity;
import com.gmail.kulacholeg.JFSD_2022_09_11.entity.GenreEntity;
import com.gmail.kulacholeg.JFSD_2022_09_11.exception.NotFoundException;
import com.gmail.kulacholeg.JFSD_2022_09_11.repository.BookRepository;
import com.gmail.kulacholeg.JFSD_2022_09_11.repository.GenreRepository;
import com.gmail.kulacholeg.JFSD_2022_09_11.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    @Override
    public int createBook(BookSaveDto dto) {
        return bookRepository.save(bookDtoToBookEntity(dto, new BookEntity())).getId();
    }

    @Override
    public BookDetailsDto getBook(int id) {
        return bookEntityToBookDto(getOrElseThrow(id));
    }

    @Override
    public BookDetailsDto updateBook(int id, BookSaveDto dto) {
        BookEntity entity = bookDtoToBookEntity(dto, getOrElseThrow(id));
        bookRepository.save(entity);
        return bookEntityToBookDto(entity);
    }

    @Override
    public List<BookDetailsDto> search(BookQueryDto query){
        List<BookDetailsDto> result;

        if (query == null || (query.getAuthor() == null && query.getGenre() == null)){
            result = checkListThenConvert(bookRepository.findAll());
            if(query != null) result = paginate(result, query);
            return result;
        }
        if (query.getGenre() == null){
            result = checkListThenConvert(bookRepository.findByAuthor(query.getAuthor()));
            return paginate(result, query);
        }

        GenreEntity genre = genreRepository.findByNameIgnoreCase(query.getGenre());

        if (query.getAuthor() == null){
            result = checkListThenConvert(bookRepository.findByGenre(genre));
            return paginate(result, query);
        }

        result = checkListThenConvert(bookRepository.findByAuthorAndGenre(query.getAuthor(), genre));
        return paginate(result, query);
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.delete(getOrElseThrow(id));
    }

    public BookEntity bookDtoToBookEntity(BookSaveDto dto, BookEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setGenre(genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new IllegalArgumentException("No genre with id %d".formatted(dto.getGenreId()))));
        return entity;
    }

    public BookDetailsDto bookEntityToBookDto(BookEntity entity){
        return BookDetailsDto.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .genre(entity.getGenre().getName())
                .title(entity.getTitle())
                .build();

    }

    public List<BookDetailsDto> checkListThenConvert(List<BookEntity> list){
        if(list.isEmpty()) throw new NotFoundException("No items found");
        return list.stream()
                .map(this::bookEntityToBookDto)
                .toList();

    }

    private BookEntity getOrElseThrow(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No book with id %d".formatted(id)));
    }

    public List<BookDetailsDto> paginate(List<BookDetailsDto> list, BookQueryDto query){
        Integer size = query.getSize();
        Integer page = query.getPage();
        if(size == null || size <= 0) size = list.size();
        if(page == null || page < 0) page = 0;
        if(page*size > list.size()){
            throw new NotFoundException("No items found");
        }
        return list.stream().skip((long) page * size).limit(size).toList();
    }
}

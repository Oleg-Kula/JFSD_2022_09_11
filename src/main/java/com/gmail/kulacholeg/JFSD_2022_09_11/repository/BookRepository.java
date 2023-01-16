package com.gmail.kulacholeg.JFSD_2022_09_11.repository;

import com.gmail.kulacholeg.JFSD_2022_09_11.entity.BookEntity;
import com.gmail.kulacholeg.JFSD_2022_09_11.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findByAuthorAndGenre(String author, GenreEntity genre);

    List<BookEntity> findByGenre(GenreEntity genre);

    List<BookEntity> findByAuthor(String author);
}

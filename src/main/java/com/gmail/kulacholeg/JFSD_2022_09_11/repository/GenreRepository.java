package com.gmail.kulacholeg.JFSD_2022_09_11.repository;

import com.gmail.kulacholeg.JFSD_2022_09_11.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    GenreEntity findByNameIgnoreCase(String name);
}

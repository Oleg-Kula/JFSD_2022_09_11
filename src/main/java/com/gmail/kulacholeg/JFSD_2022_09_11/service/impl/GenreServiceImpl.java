package com.gmail.kulacholeg.JFSD_2022_09_11.service.impl;

import com.gmail.kulacholeg.JFSD_2022_09_11.dto.GenreDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.entity.GenreEntity;
import com.gmail.kulacholeg.JFSD_2022_09_11.repository.GenreRepository;
import com.gmail.kulacholeg.JFSD_2022_09_11.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> getGenres() {
        return genreRepository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    public GenreDto entityToDto(GenreEntity entity) {
        return GenreDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}

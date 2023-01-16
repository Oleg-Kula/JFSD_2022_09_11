package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import com.gmail.kulacholeg.JFSD_2022_09_11.dto.GenreDto;
import com.gmail.kulacholeg.JFSD_2022_09_11.service.impl.GenreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreServiceImpl genreService;
    @GetMapping
    public List<GenreDto> getGenres(){
        return genreService.getGenres();
    }
}

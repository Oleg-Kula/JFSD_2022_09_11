package com.gmail.kulacholeg.JFSD_2022_09_11.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BookSaveDto {
    private String title;
    private String author;
    private Integer genreId;
}

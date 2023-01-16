package com.gmail.kulacholeg.JFSD_2022_09_11.dto;

import lombok.Getter;

@Getter
public class BookQueryDto {
    private String author;
    private String genre;

    private Integer size;
    private Integer page;
}

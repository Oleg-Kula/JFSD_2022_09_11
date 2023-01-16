package com.gmail.kulacholeg.JFSD_2022_09_11.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@RequiredArgsConstructor
public class RestResponse {
    private final String result;
}

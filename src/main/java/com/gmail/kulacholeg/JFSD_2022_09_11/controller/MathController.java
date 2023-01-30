package com.gmail.kulacholeg.JFSD_2022_09_11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math/examples")
@RequiredArgsConstructor
public class MathController {

    @GetMapping
    public String[] getMath(@RequestParam int count){
        return new String[]{
                "5+5",
                "2-3",
                "9+9"
        };
    }
}

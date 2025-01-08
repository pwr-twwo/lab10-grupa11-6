package com.piisw.kino.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DummyControler {

    @GetMapping("/smoke")
    public String getResponse(){
        return "im working";
    }

}

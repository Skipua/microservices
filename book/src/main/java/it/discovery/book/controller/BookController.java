package it.discovery.book.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Value("${library.name}")
    private String libraryName;

    @GetMapping("/library")
    public String getLibraryName() {
        return libraryName;
    }
}

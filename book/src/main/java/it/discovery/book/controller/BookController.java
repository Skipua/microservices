package it.discovery.book.controller;

import it.discovery.book.domain.Book;
import it.discovery.book.domain.Hit;
import it.discovery.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final RestTemplate restTemplate;

    @Value("${library.name}")
    private String libraryName;

    @GetMapping("/library")
    public String getLibraryName() {
        return libraryName;
    }

    @PostMapping
    public void saveBook(@RequestBody Book book) {
        bookRepository.saveBook(book);
    }

    @GetMapping("book/{id}")
    public Book findById(@PathVariable int id) {
        Book book = bookRepository.findBookById(id);
        if (book != null) {
            addHit(book);
        }
        return book;
    }

    private void addHit(Book book) {
        Hit hit = new Hit();
        hit.setBrowser("Chrome");
        try {
            hit.setIp(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        hit.setUserName(System.getProperty("user.name"));
        //hit.setViewed(LocalDateTime.now());
        hit.setApplicationName("Library client");
        hit.setObjectId(String.valueOf(book.getId()));
        restTemplate.postForEntity("http://hit", hit, ResponseEntity.class);
    }
}

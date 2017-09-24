package it.discovery.book.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import it.discovery.book.client.HitClient;
import it.discovery.book.domain.Book;
import it.discovery.book.domain.Hit;
import it.discovery.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class BookController {

    private final BookRepository bookRepository;

    //private final RestTemplate restTemplate;

    private final HitClient hitClient;

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

    @GetMapping(path = "book/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "1")
    })
    public Book findById(@PathVariable int id) {
        Book book = bookRepository.findBookById(id);
        if (book != null) {
            addHit(book);
           // book.setHitCount(getHitCount(book));
        }
        return book;
    }

//    private int getHitCount(Book book) {
//        ResponseEntity r = restTemplate.getForEntity("http://hit/" + book.getId() + "/count", ResponseEntity.class);
//        return 1;
//    }

    public void addHit(Book book) {
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

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//
//        HttpEntity<Hit> entity = new HttpEntity<>(hit, headers);
//
//        restTemplate.exchange("http://hit", HttpMethod.POST, entity, ResponseEntity.class);
        hitClient.saveHit(hit);
    }

    public Book fallback(int id) {
        log.error("Failed to save hit");
        return null;
    }
}

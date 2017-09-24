package it.discovery.book.client;

import it.discovery.book.domain.Hit;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("hit")
public interface HitClient {

    @PostMapping(path="/", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    void saveHit(Hit hit);
}

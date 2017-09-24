package it.discovery.hit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HitApplication {
    public static void main(String[] args) {
        SpringApplication.run(HitApplication.class, args);
    }
}

package it.discovery.book.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Hit {
    private String userName;

    private String ip;

    private String browser;

    //private LocalDateTime viewed;

    private String applicationName;

    private String objectId;
}

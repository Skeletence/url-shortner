package com.example.urlshortner.service;

import com.example.urlshortner.repository.UrlRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class UrlCleanupService {

    private final UrlRepository repo;

    public UrlCleanupService(UrlRepository repo) {
        this.repo = repo;
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void deleteExpiredUrls() {
        System.out.println("Scheduled Task: Cleaning up expired links from DB...");

        repo.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
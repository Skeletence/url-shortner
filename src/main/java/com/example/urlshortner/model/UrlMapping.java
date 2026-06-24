package com.example.urlshortner.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "url_mappings",
        indexes = @Index(name = "idx_short_code", columnList = "shortCode")
)
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortCode;

    @Column(nullable = false, length = 2048) // Prevents failure on exceptionally long URLs
    private String originalUrl;

    @Column(nullable = false)
    private int clicks = 0;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
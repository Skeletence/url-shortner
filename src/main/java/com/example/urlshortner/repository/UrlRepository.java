package com.example.urlshortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortner.model.UrlMapping;

public interface UrlRepository
        extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortCode(
            String shortCode
    );

    Optional<UrlMapping> findByOriginalUrl(
            String originalUrl
    );

}
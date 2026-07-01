package com.example.urlshortner.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.urlshortner.dto.ShortenResponse;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.urlshortner.util.Base62Converter;

import com.example.urlshortner.model.UrlMapping;
import com.example.urlshortner.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository repo;

    public UrlService(UrlRepository repo) {
        this.repo = repo;
    }

    public List<UrlMapping> getAllLinks(){
        return repo.findAll();
    }

    @Transactional
    public ShortenResponse shorten(String url) {

        url = url.replace("\"", "");

        if (!isValidUrl(url)) {
            throw new InvalidURLException("Invalid URL");
        }

        Optional<UrlMapping> existing = repo.findByOriginalUrl(url);
        if (existing.isPresent()) {
            UrlMapping map = existing.get();
            return new ShortenResponse(
                    map.getOriginalUrl(),
                    map.getShortCode(),
                    "http://localhost:8081/" + map.getShortCode()
            );
        }

        UrlMapping map = new UrlMapping();
        map.setOriginalUrl(url);
        map.setShortCode("PENDING");

        UrlMapping savedPlaceholder = repo.save(map);

        String code = Base62Converter.encode(savedPlaceholder.getId());

        savedPlaceholder.setShortCode(code);
        UrlMapping finalSaved = repo.save(savedPlaceholder);

        return new ShortenResponse(
                finalSaved.getOriginalUrl(),
                finalSaved.getShortCode(),
                "http://localhost:8081/" + finalSaved.getShortCode()
        );
    }

    public String redirect(String code){
        UrlMapping map = repo.findByShortCode(code).orElseThrow();
        map.setClicks(map.getClicks()+1);
        repo.save(map);
        return map.getOriginalUrl();
    }

    public int getClicks(
            String code
    ){

        return repo
                .findByShortCode(
                        code
                )
                .orElseThrow()
                .getClicks();

    }
    @Cacheable(value = "urls", key = "#code")
    public String getOriginalUrl(String code) {
        System.out.println("DB queried");

        UrlMapping mapping = repo.findByShortCode(code)
                .orElseThrow(() -> new RuntimeException("Short Url not found"));


        return mapping.getOriginalUrl();
    }




    public boolean isValidUrl(String url){
        if (url == null || url.isBlank()){
            return false;
        }
        return url.startsWith("http://") ||
                url.startsWith("https://");
    }


}

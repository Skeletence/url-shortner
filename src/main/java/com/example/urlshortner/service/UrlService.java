package com.example.urlshortner.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

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

    public String shorten(
            String url
    ) {

        url = url.replace("\"", "");
        if (!isValidUrl(url)){
            throw new InvalidURLException("Invalid URL");
        }

        Optional<UrlMapping> existing =
                repo.findByOriginalUrl(
                        url
                );

        if (existing.isPresent()) {
            return existing
                    .get()
                    .getShortCode();
        }

        String code = UUID.randomUUID().toString().substring(0, 6);

        UrlMapping map = new UrlMapping();

        map.setOriginalUrl(url);

        map.setShortCode(code);

        repo.save(map);

        return code;

    }

    public String getOriginalUrl(
            String code
    ){

        return repo
                .findByShortCode(code)
                .orElseThrow(
                        () -> new RuntimeException("Short Url not found")
                )
                .getOriginalUrl();

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



    public boolean isValidUrl(String url){
        if (url == null || url.isBlank()){
            return false;
        }
        return url.startsWith("http://") ||
                url.startsWith("https://");
    }


}
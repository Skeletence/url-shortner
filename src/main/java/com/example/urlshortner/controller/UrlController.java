package com.example.urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.urlshortner.service.UrlService;

import java.util.List;

@RestController
public class UrlController {
    @GetMapping("/links")
    public List<String> links() {
        return service.getAllLinks().stream().map(
                link->"http://localhost:8081/"+ link.getShortCode()+" | "+link.getOriginalUrl()
        ).toList();
    }

    private final UrlService service;

    public UrlController(
            UrlService service
    ){
        this.service = service;
    }
    @PostMapping("/shorten")
    public String shorten(
            @RequestBody String url
    ){
        String code = service.shorten(url);
        return "http://localhost:8081/" + code;

    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code
    ){

        String url =
                service.redirect(code);

        return ResponseEntity
                .status(302)
                .header(
                        "Location",
                        url
                )
                .build();

    }

    @GetMapping("/stats/{code}")
    public String stats(
            @PathVariable
            String code
    ){
        int click = service.getClicks(code);
        return "Number of Clicks: " + click;


    }

    @GetMapping("/expand/{code}")
    public ResponseEntity<String> expandUrl(
            @PathVariable String code
    ){
        String originalUrl = service.getOriginalUrl(code);
        return ResponseEntity.ok(originalUrl);
    }

}
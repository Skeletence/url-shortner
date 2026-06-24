package com.example.urlshortner.dto;

public class ShortenResponse {
    private String originalUrl;
    private String shortCode;
    private String shortUrl;

    public ShortenResponse(){
    }
    public ShortenResponse(
            String originalUrl,
            String shortCode,
            String shortUrl){
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl(){
        return originalUrl;
    }
    public String getShortCode(){
        return shortCode;
    }
    public String getShortUrl(){
        return shortUrl;
    }

    public void setOriginalUrl(String originalUrl){
        this.originalUrl = originalUrl;
    }
    public void setShortCode(String shortCode){
        this.shortCode = shortCode;
    }
    public void setShortUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }
}

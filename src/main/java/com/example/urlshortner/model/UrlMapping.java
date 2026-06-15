package com.example.urlshortner.model;

import jakarta.persistence.*;

@Entity
public class UrlMapping {

    @Id

    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )

    private Long id;

    private String shortCode;

    private String originalUrl;

    private int clicks = 0;

    public int getClicks(){
        return clicks;
    }

    public void setClicks(int clicks){
        this.clicks=clicks;
    }

    public Long getId() {
        return id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(
            String shortCode
    ){
        this.shortCode =
                shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(
            String originalUrl
    ){
        this.originalUrl =
                originalUrl;
    }

}
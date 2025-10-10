package com.screensound.api.entity;

public enum Genre {

    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    Classic("Classic"),
    RNB("R&B"),
    ELECTRONIC("Electronic"),
    COUNTRY("Country"),
    UNDEFINED("Undefined");

    private final String genre;

    Genre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return genre;
    }

}

package com.screensound.api.entity;

public enum ArtistType {

    SOLO("Solo"),
    DUO("Duo"),
    BAND("Band");

    private final String artistType;

    ArtistType(String artistType){
        this.artistType = artistType;
    }
    public String getArtistType(){
        return artistType;
    }
}

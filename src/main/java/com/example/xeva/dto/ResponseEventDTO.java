package com.example.xeva.dto;

import java.time.LocalDateTime;

public class ResponseEventDTO {

    private String organizationName;
    private String eventName;
    private String tags;
    private String language;
    private LocalDateTime dateS;
    private LocalDateTime dateF;

    public ResponseEventDTO(){}

    public ResponseEventDTO(String organizationName, String eventName, String tags, String language, LocalDateTime dateS, LocalDateTime dateF) {
        this.organizationName = organizationName;
        this.eventName = eventName;
        this.tags = tags;
        this.language = language;
        this.dateS = dateS;
        this.dateF = dateF;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getDateS() {
        return dateS;
    }

    public void setDateS(LocalDateTime dateS) {
        this.dateS = dateS;
    }

    public LocalDateTime getDateF() {
        return dateF;
    }

    public void setDateF(LocalDateTime dateF) {
        this.dateF = dateF;
    }
}
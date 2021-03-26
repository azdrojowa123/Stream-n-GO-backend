package com.example.xeva.controller;

import com.example.xeva.dto.EventDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

class ObjHolder {

    EventDTO eventDTO;
    //String dateS;
    //String dateE;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateS;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateE;

    public LocalDateTime getDateS() {
        return dateS;
    }

    public void setDateS(LocalDateTime dateS) {
        this.dateS = dateS;
    }

    public LocalDateTime getDateE() {
        return dateE;
    }

    public void setDateE(LocalDateTime dateE) {
        this.dateE = dateE;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }

    /*public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }*/
}

package com.example.xeva.service.interfaces;

import com.example.xeva.model.Event;

import java.util.List;

public interface EventService {
    void save(Event event);
    List findAll();

}

package com.example.xeva.service.impl;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.model.Event;

import com.example.xeva.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }
}
package com.example.xeva.service;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.model.Event;
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

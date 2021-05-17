package com.example.xeva.service.impl;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.model.Event;

import com.example.xeva.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }

    @Override
    public List findAll() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void acceptEvent(int id) {
        eventRepository.acceptEvent(id);
    }

    @Override
    public Event findById(int id) {
        return eventRepository.findById(id);
    }
}

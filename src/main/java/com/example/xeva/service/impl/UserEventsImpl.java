package com.example.xeva.service.impl;

import com.example.xeva.dao.UserEventsRepository;
import com.example.xeva.model.UserEvents;
import com.example.xeva.service.interfaces.UserEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventsImpl implements UserEventsService {

    @Autowired
    UserEventsRepository userEventsRepository;

    @Override
    public UserEvents findById(int id) {
        return userEventsRepository.findById(id);
    }

    @Override
    public void saveEventToUser(UserEvents userEvents) {
        userEventsRepository.save(userEvents);
    }

    @Override
    public void deleteSavedEvent(int userId, int eventId) {
        userEventsRepository.deleteSaved(userId,eventId);

    }
}

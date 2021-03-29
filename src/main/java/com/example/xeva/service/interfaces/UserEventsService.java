package com.example.xeva.service.interfaces;

import com.example.xeva.model.UserEvents;

public interface UserEventsService {

    UserEvents findById(int id);
    void saveEventToUser(UserEvents userEvents);
    void deleteSavedEvent(int userId, int eventId);
}

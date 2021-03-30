package com.example.xeva.service.interfaces;

import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;

public interface UserEventsService {

    void saveEventToUser(TimeEvent timeEvent, User user);
    void deleteEventToUser(TimeEvent timeEvent, User user);
}

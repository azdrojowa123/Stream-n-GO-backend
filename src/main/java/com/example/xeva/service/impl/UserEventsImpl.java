package com.example.xeva.service.impl;

import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;
import com.example.xeva.service.interfaces.UserEventsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEventsImpl implements UserEventsService {


    @Transactional
    @Override
    public void saveEventToUser(TimeEvent timeEvent, User user) {
        user.getSavedEvents().add(timeEvent);
        timeEvent.getSavedBy().add(user);
    }


    @Transactional
    @Override
    public void deleteEventToUser(TimeEvent timeEvent, User user) {
        user.getSavedEvents().remove(timeEvent);
        timeEvent.getSavedBy().remove(user);

    }
}

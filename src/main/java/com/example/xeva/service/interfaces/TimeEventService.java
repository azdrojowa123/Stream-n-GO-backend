package com.example.xeva.service.interfaces;

import com.example.xeva.model.TimeEvent;

public interface TimeEventService {
    void save(TimeEvent timeEvent);
    TimeEvent findById(int id);
}

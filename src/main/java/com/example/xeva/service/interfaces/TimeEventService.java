package com.example.xeva.service.interfaces;

import com.example.xeva.model.TimeEvent;

import java.time.LocalDate;
import java.util.List;

public interface TimeEventService {
    void save(TimeEvent timeEvent);
    TimeEvent findById(int id);
    List<TimeEvent> findFromDay(LocalDate day);
}

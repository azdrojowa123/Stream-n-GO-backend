package com.example.xeva.service.impl;

import com.example.xeva.dao.TimeEventRepository;
import com.example.xeva.model.TimeEvent;
import com.example.xeva.service.interfaces.TimeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeEventServiceImpl implements TimeEventService {

    @Autowired
    TimeEventRepository timeEventRepository;

    @Override
    public void save(TimeEvent timeEvent) {
        timeEventRepository.save(timeEvent);
    }
}

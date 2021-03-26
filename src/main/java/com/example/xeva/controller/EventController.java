package com.example.xeva.controller;

import com.example.xeva.mapper.EventMapper;
import com.example.xeva.model.*;
import com.example.xeva.service.interfaces.EventService;
import com.example.xeva.service.interfaces.GeneratorService;
import com.example.xeva.service.interfaces.TimeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins= "*", allowedHeaders="*")
public class EventController {


    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TimeEventService timeEventService;

    @Autowired
    private GeneratorService generatorService;


    @PostMapping(value="/createEvent")
    public ResponseEntity<?> create(@Valid @RequestBody ObjHolder objHolder){
        Event event =  eventMapper.toEvent(objHolder.getEventDTO());
        eventService.save(event);
        System.out.println("dates"+ objHolder.getDateS()+objHolder.getDateF());
        if( !event.isCyclical() ){
            TimeEvent newTimeEvent = new TimeEvent(objHolder.getDateS(), objHolder.getDateF(), event);
            timeEventService.save(newTimeEvent);
        } else {

            HashMap<LocalDateTime,LocalDateTime> listWithDates = generatorService.generatedates(objHolder.getEventDTO().getDaysOfWeek(), objHolder.getDateS(), objHolder.getDateF());
            for (Map.Entry<LocalDateTime, LocalDateTime> entry : listWithDates.entrySet())
            {
                LocalDateTime startTime = entry.getKey();
                LocalDateTime endTime = entry.getValue();
                System.out.println(startTime);
                System.out.println(endTime);
                TimeEvent newTimeEvent = new TimeEvent(startTime, endTime, event);
                timeEventService.save(newTimeEvent);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/event/fetchDay") //http:localhost:8080/event/fetchDay?day=2021-03-30
    public ResponseEntity<List<TimeEvent>> create(@RequestParam String day) {

        LocalDate ld = LocalDate.parse( day );
        List<TimeEvent> listOfEvents = timeEventService.findFromDay(ld);

        return new ResponseEntity(listOfEvents, HttpStatus.OK);

    }


}
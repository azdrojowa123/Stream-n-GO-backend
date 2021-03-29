package com.example.xeva.controller;

import com.example.xeva.dto.ResponseEventDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.model.*;
import com.example.xeva.service.interfaces.*;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventsService userEventsService;


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

    //http:localhost:8080/event/fetchDay?day=2021-03-30
    @GetMapping("/event/fetchDay")
    public ResponseEntity<List<ResponseEventDTO>> create(@RequestParam String day, @RequestBody String userEmail,@RequestBody String user2) {

        LocalDate ld = LocalDate.parse( day );
        List<TimeEvent> listOfEvents = timeEventService.findFromDay(ld);
        List<ResponseEventDTO> listOfResponses = new ArrayList<>();
        System.out.println("userEmail "+userEmail);
        System.out.println("userEmail2 "+user2);
        for(TimeEvent timeEvent: listOfEvents){
            listOfResponses.add(eventMapper.toResponseEvent(timeEvent, userEmail));
        }

        return new ResponseEntity(listOfResponses, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSavedEvent")
    public ResponseEntity<?> deleteSavedEvent(@RequestBody Map<String, String> json){

        User user = userService.findByEmail(json.get("userEmail"));
        TimeEvent timeEvent = timeEventService.findById(Integer.parseInt(json.get("id")));
//        System.out.println("ilosc zapisanych wydarzen do usera przed"+ user.getSavedEvents().size());
//        System.out.println("ilosc zapisanych userow do wydarzenia przed"+ timeEvent.getSavedBy().size());
//        user.removeSavedTimeEvent(timeEvent);
//        System.out.println("ilosc zapisanych wydarzen do usera po"+ user.getSavedEvents().size());
//        System.out.println("ilosc zapisanych userow do wydarzenia po"+ timeEvent.getSavedBy().size());

        System.out.println("liczba eventow"+user.getSavedEvents().size());
        //System.out.println("Przez ile osob zapisany"+timeEvent.getSavedBy().size());
       // userEventsService.deleteSavedEvent(user.getId(),4);
//        System.out.println("liczba eventow po "+user.getSavedEvents().size());
//        System.out.println("Przez ile osob zapisany po "+timeEvent.getSavedBy().size());
        UserEvents userEvents = new UserEvents(user,timeEvent);
        userEventsService.saveEventToUser(userEvents);
         User user2 = userService.findByEmail(json.get("userEmail"));
         //System.out.println("liczba eventow po zapisaniu"+user.getSavedEventsOld().size());
        System.out.println("liczba eventow po zapisaniu"+user.getSavedEvents().size());
        //System.out.println("liczba eventow po zapisaniu"+user2.getSavedEventsOld().size());
        System.out.println("liczba eventow po zapisaniu"+user2.getSavedEvents().size());


        return new ResponseEntity<>(HttpStatus.OK);
    }


}
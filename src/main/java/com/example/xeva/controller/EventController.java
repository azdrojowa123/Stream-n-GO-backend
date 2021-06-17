package com.example.xeva.controller;

import com.example.xeva.dto.admin.*;
import com.example.xeva.dto.ResponseEventSpecificationDTO;
import com.example.xeva.dto.ResponseEventDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.model.*;
import com.example.xeva.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.method.P;
import org.springframework.ui.ModelMap;
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
        event.setStartDate(objHolder.getDateS());
        event.setEndDate(objHolder.getDateF());
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
    @GetMapping("/public/event/fetchDay")
    public ResponseEntity<List<ResponseEventDTO>> create(@RequestParam String day, @RequestHeader("From") String userEmail) {

        LocalDate ld = LocalDate.parse( day );
        List<TimeEvent> listOfEvents = timeEventService.findFromDay(ld);
        List<ResponseEventDTO> listOfResponses = new ArrayList<>();
        System.out.println("userEmail "+userEmail);
        for(TimeEvent timeEvent: listOfEvents){
            listOfResponses.add(eventMapper.toResponseEvent(timeEvent, userEmail));
        }

        return new ResponseEntity(listOfResponses, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSavedEvent")
    public ResponseEntity<?> deleteSavedEvent(@RequestBody Map<String, String> json){


        TimeEvent timeEvent = timeEventService.findById(Integer.parseInt(json.get("id")));
        User user = userService.findByEmail(json.get("userEmail"));

        userEventsService.deleteEventToUser(timeEvent,user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/saveEventForUser")
    public ResponseEntity<?> saveEventForUser(@RequestBody Map<String, String> json){


        TimeEvent timeEvent = timeEventService.findById(Integer.parseInt(json.get("id")));
        User user = userService.findByEmail(json.get("userEmail"));

        userEventsService.saveEventToUser(timeEvent,user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/event/fetchPast")
    public ResponseEntity<List<ResponseEventDTO>> fetchPast(@RequestHeader("From") String userEmail) {

        List<TimeEvent> listOfEvents = timeEventService.findPast();
        List<ResponseEventDTO> listOfResponses = new ArrayList<>();
        for(TimeEvent timeEvent: listOfEvents){
            ResponseEventDTO response = eventMapper.toResponseEvent(timeEvent, userEmail);
            if(response.isIfSaved()){
                listOfResponses.add(response);
            }
        }
        return new ResponseEntity(listOfResponses, HttpStatus.OK);
    }

    @GetMapping("/event/fetchFuture")
    public ResponseEntity<List<ResponseEventDTO>> fetchFuture(@RequestHeader("From") String userEmail) {

        List<TimeEvent> listOfEvents = timeEventService.findFuture();
        List<ResponseEventDTO> listOfResponses = new ArrayList<>();
        for(TimeEvent timeEvent: listOfEvents){
            ResponseEventDTO response = eventMapper.toResponseEvent(timeEvent, userEmail);
            if(response.isIfSaved()){
                listOfResponses.add(response);
            }
        }
        return new ResponseEntity(listOfResponses, HttpStatus.OK);
    }

    //for logged users
    @GetMapping("/fetchEvent/{id}")
    public ResponseEntity<ResponseEventSpecificationDTO> fetchEventSpecificationLogged(@PathVariable(value = "id") int id){
        TimeEvent timeEvent = timeEventService.findById(id);

        return  new ResponseEntity(eventMapper.toResponseEventSpecification(timeEvent), HttpStatus.OK);

    }
    @GetMapping("/public/fetchEvent/{id}")
    public ResponseEntity<ResponseEventSpecificationDTO> fetchEventSpecification(@PathVariable(value = "id") int id){
        TimeEvent timeEvent = timeEventService.findById(id);
        ResponseEventSpecificationDTO response = eventMapper.toResponseEventSpecification(timeEvent);
        response.setWebAddress("");
        return  new ResponseEntity(response, HttpStatus.OK);

    }
    @GetMapping("/admin/getEventList")
    public ResponseEntity<PAdminGetListDTO> fetchEventAdminPanel(@RequestParam int page, @RequestParam int perPage){

        List<ResponseEventAdminDTO> resultList = new ArrayList<>();
        List<Event> eventsList = eventService.findAll();
        int first = page*perPage;
        int last = first + perPage;
        if (last > eventsList.size()){
            last = eventsList.size();
        }
        for(int i = first; i<last; i++){
            resultList.add(eventMapper.toResponseEventAdmin(eventsList.get(i)));
        }
        String temp = "events "+page+"-"+perPage+"/"+eventsList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Range", temp);
        responseHeaders.set("Access-Control-Expose-Headers",
                "Content-Range");

        PAdminGetListDTO responseBody = new PAdminGetListDTO(resultList,eventsList.size());
        
        return new ResponseEntity(responseBody, responseHeaders,  HttpStatus.OK);
    }

    @GetMapping("/admin/getOneEvent/{id}")
    public ResponseEntity<ResponseEventAdminDTO> fetchOneAdminEvent(@PathVariable(value = "id") int id){
        Event event = eventService.findById(id);
        ResponseEventAdminDTO eventDTO = eventMapper.toResponseEventAdmin(event);
        return new ResponseEntity(eventDTO,  HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteEvent/{id}")
    public ResponseEntity<ResponseEventAdminDTO> deleteEventAdmin(@PathVariable(value = "id") int id){
        Event event = eventService.findById(id);
        ResponseEventAdminDTO eventDTO = eventMapper.toResponseEventAdmin(event);
        eventService.deleteById(id);
        return new ResponseEntity(eventDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/updateEvent")
    public ResponseEntity<ResponseEventAdminDTO> updateEvent(@RequestBody ResponseEventAdminDTO event){
        Event updatedEvent = eventMapper.changeUpdateEvent(event, eventService.findById(event.geId()));
        eventService.save(updatedEvent);
        ResponseEventAdminDTO newResponse = eventMapper.toResponseEventAdmin(updatedEvent);
        return new ResponseEntity(newResponse, HttpStatus.OK);

    }

    @PostMapping("/admin/acceptEvent/{id}")
    public ResponseEntity<?> acceptEvent(@PathVariable(value = "id") int id){
        eventService.acceptEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
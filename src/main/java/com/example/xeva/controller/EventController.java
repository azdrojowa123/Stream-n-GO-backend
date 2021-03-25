package com.example.xeva.controller;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dao.RoleRepository;
import com.example.xeva.dao.UserRepository;
import com.example.xeva.dto.EventDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.model.*;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;
import com.example.xeva.service.interfaces.EventService;
import com.example.xeva.service.interfaces.TimeEventService;
import com.example.xeva.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;


@RestController
@CrossOrigin(origins= "*", allowedHeaders="*")
public class EventController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TimeEventService timeEventService;

    @PostMapping(value="/createEvent")
    public ResponseEntity<?> create(@Valid @RequestBody EventDTO eventDTO){

        Event event =  eventMapper.toEvent(eventDTO);
        eventService.save(event);
        if( !event.isCyclical() ){
            Timestamp dateB = Timestamp.valueOf(String.format("%04d-%02d-%02d %02d:%02d:00",
                    2021, 3, 25, 13, 30));
            Timestamp dateE = Timestamp.valueOf(String.format("%04d-%02d-%02d %02d:%02d:00",
                    2021, 3, 25, 14, 30));
            TimeEvent te = new TimeEvent(dateB, dateE, event);
            timeEventService.save(te);
        }

        return ResponseEntity.ok().build();
    }
}
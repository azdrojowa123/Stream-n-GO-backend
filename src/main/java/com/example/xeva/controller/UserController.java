package com.example.xeva.controller;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dao.RoleRepository;
import com.example.xeva.dao.UserRepository;
import com.example.xeva.model.Event;
import com.example.xeva.model.JwtRequest;
import com.example.xeva.model.Organization;
import com.example.xeva.model.Role;
import com.example.xeva.model.User;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;
import com.example.xeva.service.EventService;
import com.example.xeva.service.RoleService;
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
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin(origins= "*", allowedHeaders="*")
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EventService eventService;


    @PostMapping(value="/signin")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest req) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
        }catch (Exception e) {
            throw new Exception("invalid credentials", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

//        User user = userRepository.findById(1);
//        Organization org = organizationRepository.findById(1);
//        Organization tempOrg = new Organization("TEMP", "TEMP", "TEMP", "TEMP", "TEMP",
//                "TEMP", "TEMP", 32323232, "TEMP", "TEMP", "TEMP");
//        Organization tempOtg2 = organizationRepository.findById(14);
//        long now = System.currentTimeMillis();
//        Time time = new Time(now);
//        Event event = new Event(user,tempOtg2,"OnewOO","myname",(byte)4,  true,time,"myname","myname","myname","myname",(byte)1);
//        eventService.save(event);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
    
    @GetMapping(value="/nothing") //only for tests
    public ResponseEntity<?> not(){
        return ResponseEntity.ok("ok");
    }

}

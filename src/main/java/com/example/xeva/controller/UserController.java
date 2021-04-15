package com.example.xeva.controller;

import com.example.xeva.dao.EventRepository;
import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dao.TimeEventRepository;
import com.example.xeva.dao.UserRepository;
import com.example.xeva.dto.UserDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.mapper.UserMapper;
import com.example.xeva.model.JwtRequest;
import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;

import com.example.xeva.service.interfaces.EventService;
import com.example.xeva.service.interfaces.TimeEventService;
import com.example.xeva.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
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

    @Autowired
    private UserService userService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TimeEventService timeEventService;

    @Autowired
    private TimeEventRepository timeEventRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value="/notLogged/signin")
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest req) throws Exception {
       try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
        }catch (Exception e) {
            throw new Exception("invalid credentials", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        User temp = userService.findById(1);


        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping(value="/notLogged/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) throws Exception{
        if(userService.findByEmail(userDTO.getEmail()) != null){
            throw new Exception(("Email already in use"));
        }

        if(userService.findByEmail(userDTO.getEmail()) != null){
            throw new Exception(("Email already in use"));
        }

        User user = userMapper.toUser(userDTO);
        user.setPwd(passwordEncoder.encode(userDTO.getPwd()));

        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/nothing") //only for tests
    public ResponseEntity<?> not(){

        return ResponseEntity.ok("ok");
    }

}

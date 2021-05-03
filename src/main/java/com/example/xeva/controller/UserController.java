package com.example.xeva.controller;

import antlr.Token;
import com.example.xeva.dao.EventRepository;
import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dao.TimeEventRepository;
import com.example.xeva.dao.UserRepository;
import com.example.xeva.dto.LoginResponseDTO;
import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.mapper.UserMapper;
import com.example.xeva.model.JwtRequest;
import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.TokenVerification;
import com.example.xeva.model.User;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;

import com.example.xeva.service.impl.EmailService;
import com.example.xeva.service.interfaces.EventService;
import com.example.xeva.service.interfaces.TimeEventService;
import com.example.xeva.service.interfaces.TokenService;
import com.example.xeva.service.interfaces.UserService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value="/public/signin")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody JwtRequest req) throws Exception {
       try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
        }catch (Exception e) {
            throw new Exception("invalid credentials", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);


        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token,userDetails.getAuthorities().toArray()[0].toString());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @PostMapping(value="/public/register")
    public ResponseEntity<?> register(@RequestBody NewUserDTO userDTO) throws Exception{
        if(userService.findByEmail(userDTO.getEmail()) != null){
            throw new Exception(("Email already in use"));
        }



        User user = userMapper.toNewUser(userDTO);
        user.setPwd(passwordEncoder.encode(userDTO.getPwd()));
        user.setIsEnabled(false);


        TokenVerification userToken = new TokenVerification(user);


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Dokończenie rejestracji w aplikacji Xeva");
        mailMessage.setFrom("xeva.company@gmail.com");
        mailMessage.setText("Aby potwierdzić rejestrację kliknij tutaj: "+
                "http://localhost:8080/public/confirm-account?token="+userToken.getToken());
        emailService.sendEmail(mailMessage);

        userService.save(user);
        tokenService.save(userToken);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/public/confirm-account")
    public void successfulRegister(HttpServletResponse response, @RequestParam("token")String confirmationToken) throws IOException {

        TokenVerification userTokenVerification = tokenService.findByToken(confirmationToken);
        User user = userService.findById(userTokenVerification.getUser().getId());
        userService.makeUserActiv(user.getId());

        response.sendRedirect("http://localhost:3000/sign-in");

    }


}

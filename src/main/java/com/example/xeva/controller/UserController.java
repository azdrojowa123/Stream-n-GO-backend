package com.example.xeva.controller;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dto.LoginResponseDTO;
import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.dto.ResponseEventSpecificationDTO;
import com.example.xeva.dto.UserDTO;
import com.example.xeva.dto.admin.ResponseEventAdminDTO;
import com.example.xeva.dto.admin.ResponseUserAdminDTO;
import com.example.xeva.mapper.EventMapper;
import com.example.xeva.mapper.UserMapper;
import com.example.xeva.model.*;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;

import com.example.xeva.service.impl.EmailService;
import com.example.xeva.service.interfaces.EventService;
import com.example.xeva.service.interfaces.TokenService;
import com.example.xeva.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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
    private OrganizationRepository organizationRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventMapper eventMapper;


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
        if(user.getIsEnabled() == true){
            response.sendRedirect("http://localhost:3000/registration-link-expired");
        } else {
            userService.makeUserActiv(user.getId());
            response.sendRedirect("http://localhost:3000/sign-in?register=true");
        }


    }

    @DeleteMapping("/admin/users/deleteUser/{id}")
    public ResponseEntity<ResponseUserAdminDTO> deleteUserAdmin(@PathVariable(value = "id") int id){
        User user = userService.findById(id);
        System.out.println(user.toString());
        ResponseUserAdminDTO userDTO = userMapper.toResponseUserAddminDTO(user);
        userService.deleteByID(id);
        return new ResponseEntity(userDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/users/updateUser")
    public ResponseEntity<ResponseUserAdminDTO> updateUserAdmin(@RequestBody UserDTO userDTO){
        User mappedUser = userMapper.toUser(userDTO);
        System.out.println("ROLE1" + mappedUser.getRole().toString());
        User updatedUser = userMapper.changeUpdateUser(mappedUser, userService.findById(mappedUser.getId()));
        System.out.println("ROLE2" + updatedUser.getRole().toString());
        userService.save(updatedUser);
        ResponseUserAdminDTO responseUserDTO = userMapper.toResponseUserAddminDTO(updatedUser);
        System.out.println("ROLE3" + responseUserDTO.getRole().toString());
        return new ResponseEntity(responseUserDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/users/getOne/{id}")
    public ResponseEntity<ResponseUserAdminDTO> getUser(@PathVariable(value = "id") int id){
        User user = userService.findById(id);
        ResponseUserAdminDTO userDTO = userMapper.toResponseUserAddminDTO(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }
}

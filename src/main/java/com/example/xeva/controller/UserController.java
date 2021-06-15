package com.example.xeva.controller;

import com.example.xeva.dto.LoginResponseDTO;
import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.dto.UserDTO;
import com.example.xeva.dto.admin.PAdminGetListOfUsersDTO;
import com.example.xeva.dto.admin.ResponseUserAdminDTO;
import com.example.xeva.mapper.UserMapper;
import com.example.xeva.model.*;
import com.example.xeva.security.JwtTokenUtil;
import com.example.xeva.security.UserDetailsImpl;

import com.example.xeva.service.impl.EmailService;
import com.example.xeva.service.interfaces.TokenService;
import com.example.xeva.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    public String createEmailMessage(String userToken, String userEmail) throws IOException {

        Scanner scanner = new Scanner(Paths.get("bef_email.txt"), StandardCharsets.UTF_8.name());
        String bef_email = scanner.useDelimiter("\\A").next();
        scanner = new Scanner(Paths.get("after_email.txt"), StandardCharsets.UTF_8.name());
        String after_email = scanner.useDelimiter("\\A").next();
        scanner = new Scanner(Paths.get("after_link.txt"), StandardCharsets.UTF_8.name());
        String after_link = scanner.useDelimiter("\\A").next();
        scanner.close();
        String link = "http://localhost:8080/public/confirm-account?token="+userToken;
        StringBuilder emailLink = new StringBuilder();
        emailLink.append("<a href=");
        emailLink.append("mailto:");
        emailLink.append(userEmail);
        emailLink.append(">");
        emailLink.append(userEmail);
        emailLink.append("</a>");
        StringBuilder sb = new StringBuilder();
        sb.append(bef_email);
        sb.append(emailLink.toString());
        sb.append(after_email);
        sb.append(link);
        sb.append(after_link);
        return sb.toString();
    }

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
        String emailContent = createEmailMessage(userToken.getToken(), user.getEmail());
        MimeMessage mimeMessage = emailService.createMime();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(emailContent, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Welcome to STREAM'N GO, registration confirmation");
        helper.setFrom("xeva.company@gmail.com");
        emailService.sendEmailWithHtml(mimeMessage);
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
        ResponseUserAdminDTO userDTO = userMapper.toResponseUserAddminDTO(user);
        userService.deleteByID(id);
        return new ResponseEntity(userDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/admin/users/updateUser")
    public ResponseEntity<ResponseUserAdminDTO> updateUserAdmin(@RequestBody UserDTO userDTO){
        User mappedUser = userMapper.toUser(userDTO);
        User updatedUser = userMapper.changeUpdateUser(mappedUser, userService.findById(mappedUser.getId()));
        userService.save(updatedUser);
        ResponseUserAdminDTO responseUserDTO = userMapper.toResponseUserAddminDTO(updatedUser);
        return new ResponseEntity(responseUserDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/users/getOne/{id}")
    public ResponseEntity<ResponseUserAdminDTO> getUserByID(@PathVariable(value = "id") int id){
        User user = userService.findById(id);
        ResponseUserAdminDTO userDTO = userMapper.toResponseUserAddminDTO(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/users/getOne")
    public ResponseEntity<ResponseUserAdminDTO> getUserByEmail(@RequestBody String email){
        User user = userService.findByEmail(email);
        ResponseUserAdminDTO userDTO = userMapper.toResponseUserAddminDTO(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @GetMapping("/admin/getUserList")
    public ResponseEntity<ResponseUserAdminDTO> fetchUserAdminPanel(@RequestParam int page, @RequestParam int perPage){

        List<ResponseUserAdminDTO> resultList = new ArrayList<>();
        List<User> usersList = userService.findAll();
        int first = page*perPage;
        int last = first + perPage;
        if (last > usersList.size()){
            last = usersList.size();
        }
        for(int i = first; i<last; i++){
            resultList.add(userMapper.toResponseUserAddminDTO(usersList.get(i)));
        }
        String temp = "users "+page+"-"+perPage+"/"+usersList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Range", temp);
        responseHeaders.set("Access-Control-Expose-Headers",
                "Content-Range");

        PAdminGetListOfUsersDTO responseBody = new PAdminGetListOfUsersDTO(resultList,usersList.size());

        return new ResponseEntity(responseBody, responseHeaders,  HttpStatus.OK);
    }
}

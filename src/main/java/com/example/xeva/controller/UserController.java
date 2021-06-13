package com.example.xeva.controller;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dto.LoginResponseDTO;
import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.dto.ResponseEventSpecificationDTO;
import com.example.xeva.dto.UserDTO;
import com.example.xeva.dto.admin.PAdminGetListDTO;
import com.example.xeva.dto.admin.PAdminGetListOfUsersDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
        mailMessage.setText("<p>Simple HTML.</p>\n" +
                "<h3><em>So very simple.</em></h3>\n" +
                "<p><span style=\"color: #ff0000;\">Lame joke that follows.</span></p>\n" +
                "<p>\n" +
                "  <span style=\"color: #ff0000;\"\n" +
                "    ><img\n" +
                "      src=\"https://afinde-production.s3.amazonaws.com/uploads/981ebabb-5722-44c1-ad30-fc57fbc8ee9d.jpeg\"\n" +
                "      alt=\"Lame joke\"\n" +
                "      width=\"245\"\n" +
                "      height=\"221\"\n" +
                "  /></span>\n" +
                "</p>\n" +
                "<h2 style=\"padding-left: 30px;\">do you?</h2>\n" +
                "<ul>\n" +
                "  <li>yes</li>\n" +
                "  <li>no</li>\n" +
                "  <li><strong>not entirely sure</strong></li>\n" +
                "</ul>"+
                "http://localhost:8080/public/confirm-account?token="+userToken.getToken());


        MimeMessage mimeMessage = emailService.createMime();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<p>Simple HTML.</p>\n" +
                "<h3><em>So very simple.</em></h3>\n" +
                "<p><span style=\"color: #ff0000;\">Lame joke that follows.</span></p>\n" +
                "<p>\n" +
                "  <span style=\"color: #ff0000;\"\n" +
                "    ><img\n" +
                "      src=\"https://afinde-production.s3.amazonaws.com/uploads/981ebabb-5722-44c1-ad30-fc57fbc8ee9d.jpeg\"\n" +
                "      alt=\"Lame joke\"\n" +
                "      width=\"245\"\n" +
                "      height=\"221\"\n" +
                "  /></span>\n" +
                "</p>\n" +
                "<h2 style=\"padding-left: 30px;\">do you?</h2>\n" +
                "<ul>\n" +
                "  <li>yes</li>\n" +
                "  <li>no</li>\n" +
                "  <li><strong>not entirely sure</strong></li>\n" +
                "</ul>";
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(user.getEmail());
        helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
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

package com.example.xeva.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "username can not be empty")
    @Email(message = "email must be a valid email address")
    private String username;

    @NotEmpty(message = "password can not be empty")
    private String password;

    public JwtRequest(){}

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.xeva.dto;

import com.example.xeva.model.Role;

public class NewUserDTO {

    public String name;
    public String email;
    public String pwd;

    public NewUserDTO(String name, String email, String pwd, String roleName) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public NewUserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

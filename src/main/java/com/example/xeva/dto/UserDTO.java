package com.example.xeva.dto;

import com.example.xeva.model.Role;

public class UserDTO {

    public String name;
    public String email;
    public String pwd;
    public Role role;

    public UserDTO(String name, String email, String pwd, Role role) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    public UserDTO() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role=" + role +
                '}';
    }
}

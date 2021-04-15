package com.example.xeva.dto;

import com.example.xeva.model.Role;

public class UserDTO {

    public String name;
    public String email;
    public String pwd;
    public String roleName;

    public UserDTO(String name, String email, String pwd, String roleName) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.roleName = roleName;
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

    public String getRole() {
        return roleName;
    }

    public void setRole(String role) {
        this.roleName = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", roleName=" + roleName +
                '}';
    }
}

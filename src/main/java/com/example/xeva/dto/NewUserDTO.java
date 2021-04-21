package com.example.xeva.dto;

import com.example.xeva.model.Role;

public class NewUserDTO {

    public String name;
    public String surname;
    public String email;
    public String pwd;

    public NewUserDTO(String name, String surname, String email, String pwd, String roleName) {
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
        return "NewUserDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}

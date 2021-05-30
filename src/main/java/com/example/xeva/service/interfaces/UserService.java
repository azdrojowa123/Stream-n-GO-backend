package com.example.xeva.service.interfaces;

import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void save(User user);
    User findById(int id);
    User findByEmail(String email);
    void makeUserActiv(int id);
    void deleteByID(int id);
}

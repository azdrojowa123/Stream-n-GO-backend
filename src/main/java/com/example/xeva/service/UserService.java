package com.example.xeva.service;

import com.example.xeva.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    void save(User user);
    User findById(int id);

}

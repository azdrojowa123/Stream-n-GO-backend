package com.example.xeva.service.impl;

import com.example.xeva.dao.UserRepository;
import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;
import com.example.xeva.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void makeUserActiv(int id) {
        userRepository.updateUserToActive(id);
    }


}

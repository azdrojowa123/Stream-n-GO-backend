package com.example.xeva.service;

import com.example.xeva.dao.UserRepository;
import com.example.xeva.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

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
}

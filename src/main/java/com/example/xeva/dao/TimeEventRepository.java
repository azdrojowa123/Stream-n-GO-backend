package com.example.xeva.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;

public interface TimeEventRepository extends JpaRepository<TimeEvent,Integer>{
	
	List findAll();
    User findById(int id);
}

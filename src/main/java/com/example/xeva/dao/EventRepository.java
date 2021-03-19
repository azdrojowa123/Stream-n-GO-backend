package com.example.xeva.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.xeva.model.Event;

@Repository
public interface EventRepository  extends JpaRepository<Event,Integer> {

	List findAll();
	Event findByName(String name);
	Event findById(int id);
}

package com.example.xeva.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;
import org.springframework.data.jpa.repository.Query;

public interface TimeEventRepository extends JpaRepository<TimeEvent,Integer>{
	
	List findAll();
    TimeEvent findById(int id);

    @Query(value = "SELECT * FROM time_events u WHERE DATE(Start_date) = ?1", nativeQuery = true)
    List<TimeEvent> findFromDay(LocalDate date);

}

package com.example.xeva.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.xeva.model.Event;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface EventRepository  extends JpaRepository<Event,Integer> {

	List findAll();
	Event findByName(String name);
	Event findById(int id);

	@Modifying
	@Query(value = "UPDATE Events e SET e.status = true WHERE e.id = ?1", nativeQuery = true)
	void acceptEvent(int id);
}

package com.example.xeva.dao;

import com.example.xeva.model.Event;
import com.example.xeva.model.UserEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserEventsRepository extends JpaRepository<Event,Integer> {

    UserEvents findById(int id);

    @Transactional
    void save(UserEvents userEvents);

    @Modifying
    @Transactional
    @Query(value = " Delete from user_events u where user_id = ?1 and time_event_id = ?2", nativeQuery = true)
    void deleteSaved(int userId, int timeEventId);

}

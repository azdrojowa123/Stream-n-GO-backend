package com.example.xeva.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="user_events")
public class UserEvents {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User usersId;

    @ManyToOne
    @JoinColumn(name="time_event_id", nullable = false)
    private TimeEvent timeEventId;

    public UserEvents(){}

    public UserEvents(int id, User users, TimeEvent timeEventId) {
        this.id = id;
        this.usersId = users;
        this.timeEventId = timeEventId;
    }

    public UserEvents( User users, TimeEvent timeEventId) {
        this.usersId = users;
        this.timeEventId = timeEventId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsers() {
        return usersId;
    }

    public void setUsers(User users) {
        this.usersId = users;
    }

    public TimeEvent getTimeEventId() {
        return timeEventId;
    }

    public void setTimeEventId(TimeEvent timeEventId) {
        this.timeEventId = timeEventId;
    }
}

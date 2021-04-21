package com.example.xeva.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY) //powiazanie do tabeli Event
	Set<Event> ownedEvent;

    @ManyToOne
    @JoinColumn(name="Role_id", nullable = false)
    private Role role;



    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST})
    @JoinTable(
            name="user_events",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="time_event_id")
    )
    Set<TimeEvent> savedEvents;


    public Set<TimeEvent> getSavedEvents() {
        return savedEvents;
    }

    public void setSavedEvents(Set<TimeEvent> savedEvents) {
        this.savedEvents = savedEvents;
    }

    public User(String name, String surname, String email, String pwd, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    public User() {
    }


    public Set<Event> getOwnedEvent() {
        return ownedEvent;
    }

    public void setOwnedEvent(Set<Event> users) {
        this.ownedEvent = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

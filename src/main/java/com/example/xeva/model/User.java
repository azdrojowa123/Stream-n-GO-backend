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

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	Set<Event> users;

    @ManyToOne
    @JoinColumn(name="Role_id", nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
            name="user_events",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="time_event_id")
    )
    Set<TimeEvent> savedEvents;

    public User(String name, String email, String pwd, Role role) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    public User() {
    }

    public Set<Event> getUsers() {
        return users;
    }

    public void setUsers(Set<Event> users) {
        this.users = users;
    }

    public Set<TimeEvent> getSavedEvents() {
        return savedEvents;
    }

    public void setSavedEvents(Set<TimeEvent> savedEvents) {
        this.savedEvents = savedEvents;
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

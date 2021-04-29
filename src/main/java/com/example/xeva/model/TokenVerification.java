package com.example.xeva.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="token_verification")
public class TokenVerification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="token")
    private String token;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    public TokenVerification(){}

    public TokenVerification(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }

    public TokenVerification(int id, String token, User user) {
        this.id = id;
        this.token = token;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.example.xeva.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "roles name cannot be empty")
    private String roleName;

    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
    private Set<User> users;

    public Role(){

    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }

    public Role(String rolesName) {
        this.roleName = rolesName;
    }
}

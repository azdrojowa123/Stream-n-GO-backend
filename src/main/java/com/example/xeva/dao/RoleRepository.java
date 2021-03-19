package com.example.xeva.dao;

import com.example.xeva.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository <Role,Integer> {

    List findAll();
    Role findByRoleName(String name);

//    @Query("SELECT t FROM Role t WHERE t.name = ?1")
//    Role findByName(String name);

}

package com.example.xeva.dao;

import com.example.xeva.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List findAll();
    User findByEmail(String email);
    User findById(int id);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.isEnabled = true where u.id = :id")
    void updateUserToActive(@Param(value = "id") int id);
}

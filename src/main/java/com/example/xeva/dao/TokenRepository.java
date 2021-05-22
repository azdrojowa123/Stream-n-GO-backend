package com.example.xeva.dao;

import com.example.xeva.model.TokenVerification;
import com.example.xeva.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenVerification,Integer> {

    TokenVerification findById(int id);
    TokenVerification findByToken(String token);


}

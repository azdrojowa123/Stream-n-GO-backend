package com.example.xeva.dao;

import com.example.xeva.model.TokenVerification;
import com.example.xeva.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenVerification,Integer> {

    TokenVerification findById(int id);
}

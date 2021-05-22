package com.example.xeva.service.interfaces;

import antlr.Token;
import com.example.xeva.model.TokenVerification;
import com.example.xeva.model.User;

import java.util.List;

public interface TokenService {

    List<TokenVerification> findAll();
    void save(TokenVerification token);
    TokenVerification findById(int id);
    TokenVerification findByToken(String token);

}

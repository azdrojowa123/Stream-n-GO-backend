package com.example.xeva.service.impl;

import com.example.xeva.dao.TokenRepository;
import com.example.xeva.model.TokenVerification;
import com.example.xeva.service.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class TokenVerificationImpl implements TokenService {

    @Autowired
    public TokenRepository tokenRepository;

    @Override
    public List<TokenVerification> findAll() {
        return tokenRepository.findAll();
    }

    @Override
    public void save(TokenVerification token) {
        tokenRepository.save(token);

    }

    @Override
    public TokenVerification findById(int id) {
        return tokenRepository.findById(id);
    }
}

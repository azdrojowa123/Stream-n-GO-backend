package com.example.xeva.service;

import com.example.xeva.dao.RoleRepository;
import com.example.xeva.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String rolename) {
        return roleRepository.findByRoleName(rolename);
    }
}

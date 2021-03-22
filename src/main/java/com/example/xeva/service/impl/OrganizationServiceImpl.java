package com.example.xeva.service.impl;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.model.Organization;
import com.example.xeva.service.interfaces.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public Organization findById(int id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Organization findByName(String name) {
        return organizationRepository.findByName(name);
    }
}

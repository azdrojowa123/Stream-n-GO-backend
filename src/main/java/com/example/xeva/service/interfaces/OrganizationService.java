package com.example.xeva.service.interfaces;

import com.example.xeva.model.Organization;

import java.util.List;

public interface OrganizationService {

    Organization findById(int id);
    Organization findByName(String name);
    List<Organization> findAll();
}

package com.example.xeva.service.interfaces;

import com.example.xeva.model.Organization;

public interface OrganizationService {

    Organization findById(int id);
    Organization findByName(String name);
}

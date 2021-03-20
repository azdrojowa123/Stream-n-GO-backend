package com.example.xeva.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.xeva.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Integer>{

	List findAll();
	Organization findByName(String name);
	Organization findById(int id);
}

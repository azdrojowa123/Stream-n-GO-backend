package com.example.xeva.controller;

import com.example.xeva.dto.ResponseEventSpecificationDTO;
import com.example.xeva.dto.ResponseOrgDTO;
import com.example.xeva.mapper.OrganizationMapper;
import com.example.xeva.model.Organization;
import com.example.xeva.service.interfaces.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= "*", allowedHeaders="*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @GetMapping("/fetchOrganization")
    public ResponseEntity<ResponseOrgDTO> fetchOrganization(@RequestParam int id){
        Organization org = organizationService.findById(id);
        ResponseOrgDTO response = organizationMapper.toResponseOrganization(org);

        return  new ResponseEntity(response, HttpStatus.OK);
    }
}

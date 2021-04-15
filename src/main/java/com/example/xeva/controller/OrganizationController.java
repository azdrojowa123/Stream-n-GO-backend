package com.example.xeva.controller;

import com.example.xeva.dto.FullOrgResponseDTO;
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
    public ResponseEntity<FullOrgResponseDTO> fetchOrganization(@RequestParam int id){
        Organization org = organizationService.findById(id);
        FullOrgResponseDTO response = organizationMapper.toFullOrgResponseDTO(org);

        return  new ResponseEntity(response, HttpStatus.OK);
    }
}

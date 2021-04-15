package com.example.xeva.mapper;

import com.example.xeva.dto.ResponseOrgDTO;
import com.example.xeva.model.Organization;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public class OrganizationMapper {

     public ResponseOrgDTO toResponseOrganization(Organization organization){
         ResponseOrgDTO response = new ResponseOrgDTO();
         response.setId(organization.getId());
         response.setName(organization.getName());
         response.setPhoto(organization.getPhoto());
         response.setCity(organization.getCity());
         response.setProvince(organization.getProvince());
         response.setCountry(organization.getCountry());

         return response;
     }
}

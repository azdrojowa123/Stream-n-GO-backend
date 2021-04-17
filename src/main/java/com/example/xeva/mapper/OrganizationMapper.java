package com.example.xeva.mapper;

import com.example.xeva.dto.FullOrgResponseDTO;
import com.example.xeva.dto.ResponseOrgDTO;
import com.example.xeva.model.Organization;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public class OrganizationMapper {

     public FullOrgResponseDTO toFullOrgResponseDTO(Organization organization){
         FullOrgResponseDTO response = new FullOrgResponseDTO();
         response.setName(organization.getName());
         response.setCountry(organization.getCountry());
         response.setProvince(organization.getProvince());
         response.setCity(organization.getCity());
         response.setPostalCode(organization.getPostalCode());
         response.setStreet(organization.getStreet());
         response.setNip(organization.getNip());
         response.setPhoneNumber(organization.getPhoneNumber());
         response.setEmail(organization.getEmail());
         response.setWebPage(organization.getWebPage());
         response.setPhoto(organization.getPhoto());

         return response;
     }

     public ResponseOrgDTO responseOrgDTO(Organization organization){
         ResponseOrgDTO response = new ResponseOrgDTO();
         response.setId(organization.getId());
         response.setCity(organization.getCity());
         response.setCountry(organization.getCountry());
         response.setName(organization.getName());
         response.setPhoto(organization.getPhoto());
         response.setProvince(organization.getProvince());

         return response;
     }
}

package com.example.xeva.mapper;

import com.example.xeva.dto.FullOrgResponseDTO;
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
}

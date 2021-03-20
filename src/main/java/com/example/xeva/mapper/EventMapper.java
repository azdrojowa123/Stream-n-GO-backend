package com.example.xeva.mapper;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dao.UserRepository;
import com.example.xeva.dto.EventDTO;
import com.example.xeva.model.Event;
import com.example.xeva.model.Organization;
import com.example.xeva.model.User;
import com.example.xeva.service.UserService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = UserService.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class EventMapper {


    private UserService userService;

    private OrganizationRepository organizationRepository;

    public Event toEventMapper(EventDTO dto){
        Event newEvent = new Event();
        User eventOwner = userService.findByEmail(dto.getUsername());
        Organization org = checkIfOrgExsist(dto.getOrganization());
        newEvent.setCyclical(dto.cyclicalInt == 1);
        newEvent.setCyclical(dto.getStatusInt() == 1);

        return newEvent;
    }

   public  Organization checkIfOrgExsist(Organization org){
       if(organizationRepository.findByName(org.getName()) != null){
           return organizationRepository.findByName(org.getName());
       } else {
           return org;
       }
   }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
}

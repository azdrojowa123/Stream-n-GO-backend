package com.example.xeva.mapper;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dto.EventDTO;
import com.example.xeva.dto.ResponseEventDTO;
import com.example.xeva.model.Event;
import com.example.xeva.model.Organization;
import com.example.xeva.model.TimeEvent;
import com.example.xeva.model.User;
import com.example.xeva.service.interfaces.OrganizationService;
import com.example.xeva.service.interfaces.UserService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {UserService.class, OrganizationService.class}, injectionStrategy = InjectionStrategy.FIELD)
public abstract class EventMapper {

    private UserService userService;

    private OrganizationService organizationService;

    public Event toEvent(EventDTO dto){
        Event newEvent = new Event();
        User eventOwner = userService.findByEmail(dto.getUsername());
        Organization org = checkIfOrgExsist(dto.getOrganization());
        newEvent.setUser(eventOwner);
        newEvent.setOrganization(org);
        newEvent.setName(dto.getEventName());
        newEvent.setDescription(dto.getDescription());
        newEvent.setDaysOfWeek(dto.getDaysOfWeek());
        newEvent.setCyclical(dto.cyclicalInt == 1);
        newEvent.setMode(dto.getMode());
        newEvent.setWebAddress(dto.getWebAddress());
        newEvent.setTags(dto.getTags());
        newEvent.setLanguage(dto.getLanguage());
        newEvent.setStatus(false);

        return newEvent;
    }

    public ResponseEventDTO toResponseEvent(TimeEvent timeEvent){
        ResponseEventDTO responseEventDTO = new ResponseEventDTO();
        responseEventDTO.setOrganizationName(timeEvent.getEvent().getOrganization().getName());
        responseEventDTO.setEventName(timeEvent.getEvent().getName());
        responseEventDTO.setTags(timeEvent.getEvent().getTags());
        responseEventDTO.setLanguage(timeEvent.getEvent().getLanguage());
        responseEventDTO.setDateS(timeEvent.getStartDate());
        responseEventDTO.setDateF(timeEvent.getEndDate());

        return responseEventDTO;
    }

   public  Organization checkIfOrgExsist(Organization org){
       if(organizationService.findByName(org.getName()) != null){
           return organizationService.findByName(org.getName());
       } else {
           return org;
       }
   }


   @Autowired
    public final void setUserService(UserService userService) {

        this.userService = userService;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}

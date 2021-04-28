package com.example.xeva.mapper;

import com.example.xeva.dao.OrganizationRepository;
import com.example.xeva.dto.EventDTO;
import com.example.xeva.dto.ResponseEventDTO;
import com.example.xeva.dto.ResponseEventSpecificationDTO;
import com.example.xeva.model.*;
import com.example.xeva.service.interfaces.GeneratorService;
import com.example.xeva.service.interfaces.OrganizationService;
import com.example.xeva.service.interfaces.UserService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserService.class, OrganizationService.class}, injectionStrategy = InjectionStrategy.FIELD)
public abstract class EventMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private GeneratorService generatorService;

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

    public ResponseEventDTO toResponseEvent(TimeEvent timeEvent, String userEmail){
        ResponseEventDTO responseEventDTO = new ResponseEventDTO();
        responseEventDTO.setId(timeEvent.getId());
        responseEventDTO.setOrganizationName(timeEvent.getEvent().getOrganization().getName());
        responseEventDTO.setEventName(timeEvent.getEvent().getName());
        responseEventDTO.setTags(timeEvent.getEvent().getTags());
        responseEventDTO.setLanguage(timeEvent.getEvent().getLanguage());
        responseEventDTO.setDateS(timeEvent.getStartDate());
        responseEventDTO.setDateF(timeEvent.getEndDate());
        responseEventDTO.setTimeEventId(timeEvent.getId());
        if(userEmail.equals("empty")){
            responseEventDTO.setIfSaved(false);
        } else {
            User loggedUser = userService.findByEmail(userEmail);
            responseEventDTO.setIfSaved(checkIfEventSaved(loggedUser,timeEvent.getId()));
        }
        return responseEventDTO;
    }

    public ResponseEventSpecificationDTO toResponseEventSpecification(TimeEvent timeEvent){

        ResponseEventSpecificationDTO response = new ResponseEventSpecificationDTO();
        Organization organization = timeEvent.getEvent().getOrganization();
        response.setCyclical(timeEvent.getEvent().isCyclical());
        response.setDateS(generatorService.getOnlyDate(timeEvent.getStartDate()));
        response.setTimeS(generatorService.getOnlyTime(timeEvent.getStartDate()));
        response.setDateF(generatorService.getOnlyDate(timeEvent.getEndDate()));
        response.setTimeF(generatorService.getOnlyTime(timeEvent.getEndDate()));
        response.setLanguage(timeEvent.getEvent().getLanguage());
        response.setDescription(timeEvent.getEvent().getDescription());
        response.setOrgEmail(organization.getEmail());
        response.setOrgLogo(organization.getPhoto());
        response.setOrgName(organization.getName());
        response.setOrgWeb(organization.getWebPage());
        response.setTags(timeEvent.getEvent().getTags());
        response.setMode(timeEvent.getEvent().getMode());
        response.setDaysOfWeek(daysOfWeekFormat(timeEvent.getEvent().getDaysOfWeek()));
        response.setName(timeEvent.getEvent().getName());
        response.setWebAddress(timeEvent.getEvent().getWebAddress());


        return response;
    }

    public String daysOfWeekFormat(String daysOfWeek){
        List<String> newList = new ArrayList<>();
        List<String> daysList = Arrays.asList(daysOfWeek.split(","));
        daysList = daysList.stream().map(String :: trim).collect(Collectors.toList());
        for(String days: daysList){
            switch(days) {
                case "MONDAY":
                   newList.add("mo");
                    break;
                case "TUESDAY":
                    newList.add("tu");
                    break;
                case "WEDNESDAY":
                    newList.add("we");
                    break;
                case "THURSDAY":
                    newList.add("th");
                    break;
                case "FRIDAY":
                    newList.add("fr");
                    break;
                case "SATURDAY":
                    newList.add("sa");
                    break;
                case "SUNDAY":
                    newList.add("su");
                    break;
            }
        }
       return String.join(",", newList);
    }

   public Organization checkIfOrgExsist(Organization org){
       if(organizationService.findByName(org.getName()) != null){
           return organizationService.findByName(org.getName());
       } else {
           return org;
       }
   }

   public boolean checkIfEventSaved(User user, int timeEventId){
       boolean exists = false;
       Iterator<TimeEvent> itr = user.getSavedEvents().iterator();
        while(itr.hasNext()){
            if(itr.next().getId() == timeEventId){
                exists = true;
                break;
            }
        }
        return exists;
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

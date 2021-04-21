package com.example.xeva.mapper;

import com.example.xeva.dto.EventDTO;
import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.model.Event;
import com.example.xeva.model.Organization;
import com.example.xeva.model.Role;
import com.example.xeva.model.User;
import com.example.xeva.service.interfaces.RoleService;
import com.example.xeva.service.interfaces.UserService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {RoleService.class}, injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {

    @Autowired
    RoleService roleService;

    public User toNewUser(NewUserDTO dto){
        User newUser = new User();
        Role role = roleService.findByRoleName("User");
        newUser.setName(dto.getName());
        newUser.setSurname(dto.getSurname());
        newUser.setEmail(dto.getEmail());
        newUser.setPwd(dto.getPwd());
        newUser.setRole(role);
        return newUser;
    }
}

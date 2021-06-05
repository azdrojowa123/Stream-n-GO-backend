package com.example.xeva.mapper;

import com.example.xeva.dto.NewUserDTO;
import com.example.xeva.dto.UserDTO;
import com.example.xeva.dto.admin.ResponseEventAdminDTO;
import com.example.xeva.dto.admin.ResponseUserAdminDTO;
import com.example.xeva.model.*;
import com.example.xeva.service.interfaces.RoleService;
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

    public User toUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPwd(userDTO.getPwd());
        user.setRole(roleService.findByRoleName(userDTO.getRole()));

        return  user;
    }

    public User changeUpdateUser(User newUser, User oldUser){
        oldUser.setName(newUser.getName());
        oldUser.setSurname(newUser.getSurname());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPwd(newUser.getPwd());
        oldUser.setRole(newUser.getRole());
        return  oldUser;
    }

    public ResponseUserAdminDTO toResponseUserAddminDTO(User user){
        ResponseUserAdminDTO responseUserAdminDTO = new ResponseUserAdminDTO();
        responseUserAdminDTO.setId(user.getId());
        responseUserAdminDTO.setName(user.getName());
        responseUserAdminDTO.setSurname(user.getSurname());
        responseUserAdminDTO.setEmail(user.getEmail());
        responseUserAdminDTO.setPwd(user.getPwd());
        responseUserAdminDTO.setRole(user.getRole());

        return  responseUserAdminDTO;
    }
}

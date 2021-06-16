package com.example.xeva.dto.admin;

import com.example.xeva.dto.UserDTO;

import java.util.List;

public class PAdminGetListOfUsersDTO {
    List<UserDTO> data;
    int total;

    public PAdminGetListOfUsersDTO(List<UserDTO> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<UserDTO> getData() {
        return data;
    }

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

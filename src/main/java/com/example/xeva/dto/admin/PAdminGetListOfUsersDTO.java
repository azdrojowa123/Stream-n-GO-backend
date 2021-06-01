package com.example.xeva.dto.admin;

import java.util.List;

public class PAdminGetListOfUsersDTO {
    List<ResponseUserAdminDTO> data;
    int total;

    public PAdminGetListOfUsersDTO(List<ResponseUserAdminDTO> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<ResponseUserAdminDTO> getData() {
        return data;
    }

    public void setData(List<ResponseUserAdminDTO> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

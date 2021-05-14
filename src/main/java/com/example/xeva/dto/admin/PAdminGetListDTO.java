package com.example.xeva.dto.admin;

import java.util.List;

public class PAdminGetListDTO {

    List<ResponseEventAdminDTO> data;
    int total;

    public PAdminGetListDTO(List<ResponseEventAdminDTO> body, int total) {
        this.data = body;
        this.total = total;
    }

    public List<ResponseEventAdminDTO> getData() {
        return data;
    }

    public void setData(List<ResponseEventAdminDTO> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

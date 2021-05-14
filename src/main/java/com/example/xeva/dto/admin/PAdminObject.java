package com.example.xeva.dto.admin;

import java.util.List;

public class PAdminObject {

    List<String> sort;
    List<Integer> range;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public List<Integer> getRange() {
        return range;
    }

    public void setRange(List<Integer> range) {
        this.range = range;
    }

    public PAdminObject(){}


}

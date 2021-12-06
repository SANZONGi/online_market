package com.zjgsu.online_market.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Page {
    public Page() {
    }

    public Page(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }

    public Page(Integer current, Integer size, Integer total, List<HashMap<String,Object>> data) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.data = data;
    }

    Integer current;
    Integer size;
    Integer total;
    List<HashMap<String, Object>> data;
}

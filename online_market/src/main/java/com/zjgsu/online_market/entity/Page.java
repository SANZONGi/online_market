package com.zjgsu.online_market.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Page {
    public Page() {
    }

    public Page(Long current, Integer size) {
        this.current = current;
        this.size = size;
    }

    public Page(Long current, Integer size, Long total, List<HashMap<String,Object>> data) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.data = data;
    }

    Long current;
    Integer size;
    Long total;
    List<HashMap<String, Object>> data;
}

package com.microsoft.azure.appservice.examples.springbootmongodb.model;

import java.util.Map;

public class EventUser {

    private String id;
    private String username;
    private String status;
    private Map<String, Object> list;

    public EventUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getList() {
        return list;
    }

    public void setList(Map<String, Object> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

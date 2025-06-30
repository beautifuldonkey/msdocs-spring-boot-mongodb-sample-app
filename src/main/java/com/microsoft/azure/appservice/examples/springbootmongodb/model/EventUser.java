package com.microsoft.azure.appservice.examples.springbootmongodb.model;

import java.util.Map;

public class EventUser {

    private String userId;
    private String username;
    private String status;
    private String rejectionNotes;
    private Map<String, Object> list;

    public EventUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRejectionNotes() {
        return rejectionNotes;
    }

    public void setRejectionNotes(String rejectionNotes) {
        this.rejectionNotes = rejectionNotes;
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

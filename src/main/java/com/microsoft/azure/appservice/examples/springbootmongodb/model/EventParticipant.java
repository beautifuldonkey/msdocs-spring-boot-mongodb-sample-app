package com.microsoft.azure.appservice.examples.springbootmongodb.model;

public class EventParticipant {

    private String id;
    private Object user;

    public EventParticipant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}

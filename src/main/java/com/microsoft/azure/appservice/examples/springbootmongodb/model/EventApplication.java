package com.microsoft.azure.appservice.examples.springbootmongodb.model;

public class EventApplication {

    private String id;
    private EventUser user;

    public EventApplication() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventUser getUser() {
        return user;
    }

    public void setUser(EventUser user) {
        this.user = user;
    }
}

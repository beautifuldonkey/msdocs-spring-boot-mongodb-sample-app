package com.microsoft.azure.appservice.examples.springbootmongodb.model;

public class BdpResp {
    private String status;
    private String message;
    private String data;

    public BdpResp() {
    }

    public BdpResp(String status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

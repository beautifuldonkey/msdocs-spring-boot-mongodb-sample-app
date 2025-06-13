// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.appservice.examples.springbootmongodb.model;

import org.springframework.data.annotation.Id;

public class EoUser {

    @Id
    private String id;
    private String username;
    private boolean hasEventCreatorSub;
    private String email;
    private String authCode;
    private String authCodeExpires;

    public EoUser() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasEventCreatorSub() {
        return hasEventCreatorSub;
    }

    public void setHasEventCreatorSub(boolean hasSub) {
        this.hasEventCreatorSub = hasSub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCodeExpires() {
        return authCodeExpires;
    }

    public void setAuthCodeExpires(String authCodeExpires) {
        this.authCodeExpires = authCodeExpires;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


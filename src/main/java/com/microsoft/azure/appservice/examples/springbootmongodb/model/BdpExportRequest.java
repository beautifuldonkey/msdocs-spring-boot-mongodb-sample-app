// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azure.appservice.examples.springbootmongodb.model;

public class BdpExportRequest {

    private String email;
    private String attachmentData;

    public BdpExportRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(String attachmentData) {
        this.attachmentData = attachmentData;
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


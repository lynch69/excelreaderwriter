package com.vecv.whatsapptemplate.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDetails {
    @JsonProperty("Status")
    private String Status;
    @JsonProperty("Message")

    private String Message;

    @JsonProperty("GUIDinfo")

    private String GUIDinfo;

    // Getters and Setters
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getGUIDinfo() {
        return GUIDinfo;
    }

    public void setGUIDinfo(String guidinfo) {
        this.GUIDinfo = guidinfo;
    }

    @Override
    public String toString() {
        return "ResponseDetails{" +
                "Status='" + Status + '\'' +
                ", Message='" + Message + '\'' +
                ", GUIDinfo='" + GUIDinfo + '\'' +
                '}';
    }
}


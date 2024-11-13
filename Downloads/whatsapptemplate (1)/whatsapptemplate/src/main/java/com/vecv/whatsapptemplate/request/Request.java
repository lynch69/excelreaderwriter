package com.vecv.whatsapptemplate.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    @JsonProperty("TemplateID")

    String TemplateID;
    @JsonProperty("MobileNumber")

    String MobileNumber;

    public String getTemplateID() {
        return TemplateID;
    }

    public void setTemplateID(String templateID) {
        TemplateID = templateID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}

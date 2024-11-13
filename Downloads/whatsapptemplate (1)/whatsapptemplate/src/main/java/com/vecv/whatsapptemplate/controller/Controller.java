package com.vecv.whatsapptemplate.controller;

import com.vecv.whatsapptemplate.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vecv.whatsapptemplate.task.WhatsAppClient;
import com.vecv.whatsapptemplate.service.ExcuteAPI;

@RestController
public class Controller {
    @Autowired

    ExcuteAPI ExcuteAPI;

    @GetMapping("/test")
    public void get(){
        System.out.println("start");
        ExcuteAPI.test();
        System.out.println("end");
    }
}

package com.example.grasscutter.MobileApplication.IoT;

import com.example.grasscutter.MobileApplication.IoT.MQTTService.MqttPubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQTTController {
    @Autowired
    MqttPubSubService service;

    @PostMapping("/publish")
    public String publishMessage(){
        service.publishMessage();
        return "Message published Successfully";
    }
}


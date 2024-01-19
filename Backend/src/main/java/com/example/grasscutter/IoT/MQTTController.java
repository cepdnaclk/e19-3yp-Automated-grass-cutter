package com.example.grasscutter.IoT;

import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/iot")
public class MQTTController {
    @Autowired
    MqttPubSubService service;

    @PostMapping("/publish")
    public String publxishMessage(@RequestBody LawnmatePayload payload) throws AWSIotException, JsonProcessingException {
        service.publishMessage(payload);
        return "Message published Successfully";
    }

    @Autowired
    private MQTTConfig mqttConfig;

//    @PostMapping("/subscribe")
//    public ResponseEntity<String> subscribeToTopic() {
//        try {
//            mqttConfig.subscribeToTopic("sub");
//            return ResponseEntity.ok("Subscribed to sub");
//        } catch (AWSIotException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error subscribing to topic1");
//        }
//    }
}


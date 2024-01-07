package com.example.grasscutter.IoT.MQTTService;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.example.grasscutter.IoT.dto.LawnmatePayload;
import com.example.grasscutter.IoT.dto.MyMessage;
import com.example.grasscutter.IoT.util.MQTTConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MqttPubSubService {
    @Autowired
    MQTTConfig mqttConfig;
    public void publishMessage(LawnmatePayload payload) throws AWSIotException, JsonProcessingException {
        mqttConfig.connectToIot();
        mqttConfig.publish(payload);
    }
}

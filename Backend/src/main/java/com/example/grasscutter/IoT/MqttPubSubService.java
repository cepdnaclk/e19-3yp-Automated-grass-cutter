package com.example.grasscutter.IoT;

import com.amazonaws.services.iot.client.AWSIotException;
import com.example.grasscutter.IoT.LawnmatePayload;
import com.example.grasscutter.IoT.MQTTConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MqttPubSubService {
    @Autowired
    MQTTConfig mqttConfig;
    public void publishMessage(LawnmatePayload payload) throws AWSIotException, JsonProcessingException {
        String deviceId = payload.getDeviceId();

        mqttConfig.connectToIot(deviceId);
        mqttConfig.publish(payload, deviceId);
    }
}

package com.example.grasscutter.IoT;

import com.amazonaws.services.iot.client.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MQTTConfig {

    String clientEndpoint = "a2ol1u6jexvsgb-ats.iot.ap-south-1.amazonaws.com";   // use value returned by describe-endpoint --endpoint-type "iot:Data-ATS"
    String clientId = "LawnMate1";                               // replace with your own client ID. Use unique client IDs for concurrent connections.
    String awsAccessKeyId = "AKIAS6B2C4XUCYW6VEWM";
    String awsSecretAccessKey ="qdKyj5NC/Kv/4YT3/RPNV9A0ivdGDe+baaKLmtow";

    AWSIotMqttClient client = null;

    // AWS IAM credentials could be retrieved from AWS Cognito, STS, or other secure sources
    public void connectToIot() throws AWSIotException {
        client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);

        // optional parameters can be set before connect()
        client.connect();

        System.out.println("Connected to IOT");

    }

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ...

    public void subscribeToTopic(String topic) throws AWSIotException {
        // Make sure the client is connected before subscribing
        if (client == null || client.getConnectionStatus() != AWSIotConnectionStatus.CONNECTED) {
            connectToIot();
        }

        // Create a new AWSIotTopic object
        AWSIotTopic iotTopic = new AWSIotTopic(topic, AWSIotQos.QOS0) {
            @Override
            public void onMessage(AWSIotMessage message) {
                // Handle the incoming message
                System.out.println("Received message on topic: " + message.getTopic());
                String payload = message.getStringPayload();
                System.out.println("Message: " + payload);



                // Save the payload to MongoDB along with the userId
                saveToMongo(payload);
            }
        };

        // Subscribe to the specified topic
        client.subscribe(iotTopic, true);
    }

    private void saveToMongo( String payload) {
        try {
            // Assume a simple entity called IoTData
            IoTData iotData = new IoTData(payload);
            mongoTemplate.save(iotData);
            System.out.println("Saved to MongoDB: " + payload);
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            System.err.println("Failed to save to MongoDB: " + payload);
        }
    }



    public void publish(LawnmatePayload payload) throws AWSIotException, JsonProcessingException {
        String topic = "topic1";
        AWSIotQos qos = AWSIotQos.QOS0;
        long timeout = 3000;                    // milliseconds
        ObjectMapper mapper = new ObjectMapper();

        AWSIotDevice device = new AWSIotDevice(clientId);

        client.attach(device);
        client.connect();

        MyMessage message = new MyMessage(topic, qos, mapper.writeValueAsString(payload));
        client.publish(message,timeout);
    }

}

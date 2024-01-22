package com.example.grasscutter.MQTT;

import com.amazonaws.services.iot.client.*;
import com.example.grasscutter.MobileApplication.User.AngleDistancePair;
import com.example.grasscutter.MobileApplication.User.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class MQTTConfig {

    @Autowired
    private MongoTemplate mongoTemplate;
    String clientEndpoint = "a2ol1u6jexvsgb-ats.iot.ap-south-1.amazonaws.com";
    @Value(value = "${aws.access-key}")
    String awsAccessKeyId;
    @Value(value = "${aws.access-secret}")
    String awsSecretAccessKey;
    private List<AngleDistancePair> temporaryData = new ArrayList<>();
    private boolean addingData = true;

    @Autowired
    private UserServiceImpl userService;


    public void stopAddingData(String userId, String locationName) {
        addingData = false;

        // Save the temporary data to the user collection with location name
        userService.saveToUserMongo(userId, locationName, temporaryData);

        temporaryData.clear();
        addingData = true;
    }

    public void subscribeToTopic(String topic, String clientId) throws AWSIotException {
        clientId = clientId + "_" + UUID.randomUUID();
        System.out.println(clientId);
        AWSIotMqttClient subscriptionClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);

        subscriptionClient.connect();

        // Create a new AWSIotTopic object
        AWSIotTopic iotTopic = new AWSIotTopic(topic, AWSIotQos.QOS1) {
            @Override
            public void onMessage(AWSIotMessage message) {
                if (addingData) {
                    double angle = parseAngleFromPayload(message.getStringPayload());
                    double distance = parseDistanceFromPayload(message.getStringPayload());

                    // Create an AngleDistancePair and store it in the list
                    AngleDistancePair pair = new AngleDistancePair(angle, distance);
                    temporaryData.add(pair);
                }
            }
        };
        subscriptionClient.subscribe(iotTopic, true);
    }

    private double parseAngleFromPayload(String payload) {
        try {
            // Parse the JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);

            // Extract the angle from the JSON
            JsonNode angleNode = rootNode.path("Angle");
            if (angleNode.isNumber()) {
                return angleNode.asDouble();
            } else {
                // Handle the case where the "Angle" field is not a number
                throw new IllegalArgumentException("Invalid angle value in payload");
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during parsing
            e.printStackTrace();
            throw new IllegalArgumentException("Error parsing angle from payload");
        }
    }

    private double parseDistanceFromPayload(String payload) {
        try {
            // Parse the JSON payload
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);

            // Extract the distance from the JSON
            JsonNode distanceNode = rootNode.path("Distance");
            if (distanceNode.isNumber()) {
                return distanceNode.asDouble();
            } else {
                // Handle the case where the "Distance" field is not a number
                throw new IllegalArgumentException("Invalid distance value in payload");
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during parsing
            e.printStackTrace();
            throw new IllegalArgumentException("Error parsing distance from payload");
        }
    }

    public void publishToTopic(String topic, String clientId, List<AngleDistancePair> data) throws AWSIotException, InterruptedException {

        clientId = clientId + "_" + UUID.randomUUID();
        AWSIotMqttClient mqttClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);
        mqttClient.connect();

        String payload = convertDataToJson(data);

        AWSIotMessage message = new NonBlockingPublishListener(topic, AWSIotQos.QOS1, payload);

        mqttClient.publish(message);

        Thread.sleep(2000);

        mqttClient.disconnect();
    }

    private String convertDataToJson(List<AngleDistancePair> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting data to JSON", e);
        }
    }

    private static class NonBlockingPublishListener extends AWSIotMessage {
        public NonBlockingPublishListener(String topic, AWSIotQos qos, String payload) {
            super(topic, qos, payload);
        }

        @Override
        public void onSuccess() {
            System.out.println("Published to topic: " + getTopic());
        }

        @Override
        public void onFailure() {
            System.err.println("Failed to publish to topic: " + getTopic());
        }
    }

}

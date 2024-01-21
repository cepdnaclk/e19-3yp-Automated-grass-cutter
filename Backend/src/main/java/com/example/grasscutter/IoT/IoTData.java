package com.example.grasscutter.IoT;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document(collection = "location")
public class IoTData {

    @Id
    private String userId;
    private String payload;
    public List<AngleDistancePair> getAngleDistancePairs() {
        return angleDistancePairs;
    }

    public void setAngleDistancePairs(List<AngleDistancePair> angleDistancePairs) {
        this.angleDistancePairs = angleDistancePairs;
    }

    public void setAngleDistancePair(AngleDistancePair angleDistancePair) {
        this.angleDistancePair = angleDistancePair;
    }

    @Field("angleDistancePairs")
    private List<AngleDistancePair> angleDistancePairs;

    public IoTData() {
        // Default constructor required by Spring Data MongoDB
    }

    private AngleDistancePair angleDistancePair;

    // Constructor that takes an AngleDistancePair
    public IoTData(AngleDistancePair angleDistancePair) {
        this.angleDistancePair = angleDistancePair;
    }

    // Other fields and methods
    // ...

    // Getter for AngleDistancePair
    public AngleDistancePair getAngleDistancePair() {
        return angleDistancePair;
    }

    public IoTData(String payload) {
        this.payload = payload;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "IoTData{" +
                "userId='" + userId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}

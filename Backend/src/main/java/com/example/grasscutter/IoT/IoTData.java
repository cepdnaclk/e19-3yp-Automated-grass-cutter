package com.example.grasscutter.IoT;

import com.example.grasscutter.IoT.Data.AngleDistancePair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "location")
public class IoTData {

    @Id
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}

package com.example.grasscutter.MobileApplication.UserAuth;

import com.example.grasscutter.IoT.AngleDistancePair;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private List<String> devices;
    private Map<String, List<AngleDistancePair>> locationData;

    public void setLocationData(Map<String, List<AngleDistancePair>> locationData) {
        this.locationData = locationData;
    }



    public User(String id) {
        this.id = id;
        this.devices = new ArrayList<>();
        this.locationData = new HashMap<>();
    }

    public void addDataForLocation(String locationName, List<AngleDistancePair> data) {
        // Check if locationData is null or doesn't contain the key
        if (locationData == null) {
            locationData = new HashMap<>();
            locationData.put(locationName, data);
        } else if (!locationData.containsKey(locationName)) {
            locationData.put(locationName, data);
        } else {
            // Handle the case where the location name already exists
            throw new IllegalArgumentException("Location name '" + locationName + "' already exists. Please change the location name.");
        }
    }



    public Map<String, List<AngleDistancePair>> getLocationData() {
        return locationData;
    }
    public void addDevice(String deviceId) {
        devices.add(deviceId);
    }
    public String getUserId() {
        return id;
    }
    // Remove a device from the user's list
    public void removeDevice(String deviceId) {
        devices.remove(deviceId);
    }
    public List<String> getDevices() {
        return devices;
    }

}


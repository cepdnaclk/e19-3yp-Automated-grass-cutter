package com.example.grasscutter.MobileApplication.UserAuth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "users")
public class User {

    @Setter
    @Id
    private String userId;


    // List to store device IDs associated with the user
    private List<String> devices;
    public User(String userId) {
        this.userId = userId;
        this.devices = new ArrayList<>();

    }
// other user-related fields and methods
// getters and setters

    // Add a device to the user's list
    public void addDevice(String deviceId) {
        devices.add(deviceId);
    }

    public String getUserId() {
        return userId;
    }

    // Remove a device from the user's list
    public void removeDevice(String deviceId) {
        devices.remove(deviceId);
    }

    // Get the list of devices associated with the user
    public List<String> getDevices() {
        return devices;
    }

}


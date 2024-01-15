package com.example.grasscutter.MobileApplication.UserAuth;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "users")
public class User {

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

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


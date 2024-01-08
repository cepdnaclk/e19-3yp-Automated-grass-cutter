package com.example.grasscutter.IoT.DeviceAuth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "devices")
public class Device {

    @Id
    private String id;

    private String deviceId;
    private String deviceKey;
    private List<String> userIds;  // List of user IDs associated with the device

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    // Constructors, getters, and setters


    public Device(String deviceId, String deviceKey) {
        this.deviceId = deviceId;
        this.deviceKey = deviceKey;
    }
}

package com.example.grasscutter.IoT.DeviceAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(String deviceId) {
        return deviceRepository.findById(deviceId).orElse(null);
    }

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(Device device) {
        return deviceRepository.save(device);
    }

    public void deleteDevice(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    // Additional methods for handling user-device associations

    public void addUserToDevice(String deviceId, String userId) {
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if (device != null && !device.getUserIds().contains(userId)) {
            device.getUserIds().add(userId);
            deviceRepository.save(device);
        }
    }

    public void removeUserFromDevice(String deviceId, String userId) {
        Device device = deviceRepository.findById(deviceId).orElse(null);
        if (device != null) {
            device.getUserIds().remove(userId);
            deviceRepository.save(device);
        }
    }
}


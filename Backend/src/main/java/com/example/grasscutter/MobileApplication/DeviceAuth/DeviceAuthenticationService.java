package com.example.grasscutter.MobileApplication.DeviceAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceAuthenticationService {

    @Autowired
    private DeviceRepository deviceRepository;

    public boolean authenticateDevice(String deviceId, String password) {
        // Retrieve device from MongoDB based on the provided device ID
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if (deviceOptional.isPresent()) {
            // Device found, verify the provided password against the stored hashed password
            Device device = deviceOptional.get();
            return BCrypt.checkpw(password, device.getHashedPassword());
        }

        return false; // Device ID not found
    }

}

package com.example.grasscutter.MobileApplication.Device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceAuthenticationService {

    @Autowired
    private DeviceRepository deviceRepository;

    public boolean authenticateDevice(String deviceId, String password) {

        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);

        if (deviceOptional.isPresent()) {

            Device device = deviceOptional.get();
            return BCrypt.checkpw(password, device.getHashedPassword());

        }

        return false;
    }

}

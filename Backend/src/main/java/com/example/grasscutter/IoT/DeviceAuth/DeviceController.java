package com.example.grasscutter.IoT.DeviceAuth;

import com.example.grasscutter.MobileApplication.Auth.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/device")
public class DeviceController {

    @Autowired
    private DeviceAuthenticationService deviceAuthenticationService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/authenticate")
    public String authenticateDevice(
            @RequestParam String userId,
            @RequestParam String deviceId,
            @RequestParam String password) {
        if (deviceAuthenticationService.authenticateDevice(deviceId, password)) {
            // Check if the device is already associated with another user
            String existingUser = userService.getUserByDevice(deviceId);
            if (existingUser != null && existingUser.equals(userId)) {
                return "Device is already added to your application";
            } else if (existingUser != null) {
                return "Device is already associated with another user";
            } else {
                // If authentication is successful and the device is not associated with another user,
                // add the device to the specific user
                userService.addDeviceToUser(userId, deviceId);
                return "Authentication successful";
            }
        } else {
            return "Authentication failed";
        }
    }
}


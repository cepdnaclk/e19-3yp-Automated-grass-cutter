package com.example.grasscutter.MobileApplication.DeviceAuth;

import com.amazonaws.services.iot.client.AWSIotException;
import com.example.grasscutter.IoT.MQTTConfig;
import com.example.grasscutter.MobileApplication.UserAuth.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/device")
public class DeviceController {

    @Autowired
    private DeviceAuthenticationService deviceAuthenticationService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MQTTConfig mqttConfig;

    @PostMapping("/authenticate")
    public String authenticateDevice(
            @RequestParam String userId,
            @RequestParam String deviceId,
            @RequestParam String password) throws AWSIotException {
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
                mqttConfig.subscribeToTopic("pub", deviceId);
                return String.format("Authentication successful, subscribe to topic sub to deviceId: %s", deviceId);
            }
        } else {
            return "Authentication failed";
        }
    }

//    @GetMapping("/all")
//    public List<Device> getAllDevicesForUser(@RequestParam String userId) {
//        return deviceAuthenticationService.getAllDevicesForUser(userId);
//    }
//
//    @DeleteMapping("/delete")
//    public String deleteDevice(@RequestParam String deviceId) {
//        deviceAuthenticationService.deleteDeviceById(deviceId);
//        return "Device deleted successfully";
//    }
}


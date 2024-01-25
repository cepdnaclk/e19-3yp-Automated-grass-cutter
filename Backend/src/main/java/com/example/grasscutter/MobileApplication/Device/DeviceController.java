package com.example.grasscutter.MobileApplication.Device;

import com.amazonaws.services.iot.client.AWSIotException;
import com.example.grasscutter.MQTT.MQTTConfig;
import com.example.grasscutter.MobileApplication.User.UserServiceImpl;
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

    @Autowired
    private MQTTConfig mqttConfig;

    @PostMapping("/authenticate")
    public String authenticateDevice(
            @RequestParam String userId,
            @RequestParam String deviceId,
            @RequestParam String password) throws AWSIotException {
        if (deviceAuthenticationService.authenticateDevice(deviceId, password)) {

            String existingUser = userService.getUserByDevice(deviceId);

            if (existingUser != null && existingUser.equals(userId)) {

                return "Device is already added to your application";

            } else if (existingUser != null) {

                return "Device is already associated with another user";

            } else {

                userService.addDeviceToUser(userId, deviceId);
               // mqttConfig.subscribeToTopic(String.format("%s/pub",deviceId), userId);
                return String.format("Authentication successful, subscribe to topic sub to deviceId: %s", deviceId);
            }
        } else {

            return "Authentication failed";

        }
    }
}


package com.example.grasscutter.MobileApplication.User;

import com.amazonaws.services.iot.client.AWSIotException;
import com.example.grasscutter.MQTT.MQTTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MQTTConfig mqttConfig;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping(path = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto loginDto) {
        return ResponseEntity.ok(userService.signIn(loginDto));
    }

    @DeleteMapping(path = "/remove-device",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> removeDeviceFromUser(
            @RequestParam String userId,
            @RequestParam String deviceId) {
        try {
            userService.removeDeviceFromUser(userId, deviceId);
            return ResponseEntity.ok("Device removed from user successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error removing device from user: " + e.getMessage());
        }
    }

    @GetMapping(path = "/devices",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<String>> getAllDevicesForUser(@RequestParam String userId) {
        try {
            List<String> devices = userService.getAllDevicesForUser(userId);
            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<Map<String, List<AngleDistancePair>>> getLocationsForUser(
            @RequestParam String userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            Map<String, List<AngleDistancePair>> locationData = user.getLocationData();
            return ResponseEntity.ok(locationData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getLocationDataForUserAndLocation(
            @RequestParam String userId,
            @RequestParam String locationName,
            @RequestParam String deviceId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            Map<String, List<AngleDistancePair>> locationData = user.getLocationData();

            if (locationData.containsKey(locationName)) {
                List<AngleDistancePair> locationDetails = locationData.get(locationName);

                try {
                    mqttConfig.publishToTopic(String.format("%s/sub",deviceId),userId, locationDetails);
                    return ResponseEntity.ok(locationDetails);
                } catch (AWSIotException e) {
                    e.printStackTrace(); // Log or handle the exception appropriately
                    return ResponseEntity.status(500).body("Failed to publish message to topic");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stopAdding")
    public ResponseEntity<String> stopAddingData(@RequestParam String userId,@RequestParam String deviceId, @RequestParam String locationName) {
        try {
            mqttConfig.stopAddingData(userId, locationName);
            mqttConfig.unsubscribeFromTopic(String.format("%s/pub", deviceId), userId);
            return ResponseEntity.ok("Stopped adding data and saved to MongoDB for User ID: " + userId + ", Location: " + locationName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error stopping data addition: " + e.getMessage());
        }
    }

    @PostMapping("/select")
    public ResponseEntity<String> selectDevice(
            @RequestParam String deviceId,
            @RequestParam String userId) {
        try {
            mqttConfig.subscribeToTopic(String.format("%s/pub", deviceId), userId);
            return ResponseEntity.ok("Subscribed to device topic successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error subscribing to device topic: " + e.getMessage());
        }
    }

}

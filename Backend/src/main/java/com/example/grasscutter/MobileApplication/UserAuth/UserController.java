package com.example.grasscutter.MobileApplication.UserAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

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

}

package com.example.grasscutter.IoT.DeviceAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable String deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        if (device != null) {
            return new ResponseEntity<>(device, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> addDevice(@RequestHeader("Device-Id") String deviceId,
                                            @RequestHeader("Device-Key") String key) {
        // Validate and process the device ID and key as needed

        // Create a new Device object with the extracted information
        Device device = new Device(deviceId, key);
        device.setDeviceId(deviceId);
        device.setDeviceKey(key);

        // Add the device using your service
        Device addedDevice = deviceService.addDevice(device);

        // Return the added device in the response
        return new ResponseEntity<>(addedDevice, HttpStatus.CREATED);
    }


    @PutMapping("/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable String deviceId, @RequestBody Device device) {
        if (deviceService.getDeviceById(deviceId) != null) {
            device.setId(deviceId);
            Device updatedDevice = deviceService.updateDevice(device);
            return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String deviceId) {
        if (deviceService.getDeviceById(deviceId) != null) {
            deviceService.deleteDevice(deviceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Additional endpoints for handling user-device associations

    @PostMapping("/{deviceId}/users/{userId}")
    public ResponseEntity<Void> addUserToDevice(@PathVariable String deviceId, @PathVariable String userId) {
        deviceService.addUserToDevice(deviceId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromDevice(@PathVariable String deviceId, @PathVariable String userId) {
        deviceService.removeUserFromDevice(deviceId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


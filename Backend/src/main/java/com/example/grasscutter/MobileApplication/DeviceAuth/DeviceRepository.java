package com.example.grasscutter.MobileApplication.DeviceAuth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String> {

    // Find devices by user ID
//    List<Device> findByUserId(String userId);
//
//    // Delete a device by ID
//    void deleteById(String id);
}



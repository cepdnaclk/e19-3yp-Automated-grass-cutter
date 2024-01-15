package com.example.grasscutter.MobileApplication.DeviceAuth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}



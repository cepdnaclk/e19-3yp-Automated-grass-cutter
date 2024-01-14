package com.example.grasscutter.IoT.DeviceAuth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}



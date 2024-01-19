package com.example.grasscutter.MobileApplication.DeviceAuth;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String> {
}



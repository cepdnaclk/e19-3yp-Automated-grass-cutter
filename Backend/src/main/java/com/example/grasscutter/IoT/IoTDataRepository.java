package com.example.grasscutter.IoT;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IoTDataRepository extends MongoRepository<IoTData, String> {
    // Additional custom queries can be added here if needed
}

package com.example.grasscutter.MobileApplication.UserAuth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}


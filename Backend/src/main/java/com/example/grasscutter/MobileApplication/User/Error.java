package com.example.grasscutter.MobileApplication.User;

import lombok.Data;

@Data
public class Error {
    private String message;
    private int status;
    private Long timestamp;
}

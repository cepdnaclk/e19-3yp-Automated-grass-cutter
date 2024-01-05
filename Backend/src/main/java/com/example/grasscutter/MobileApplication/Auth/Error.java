package com.example.grasscutter.MobileApplication.Auth;

import lombok.Data;

@Data
public class Error {
    private String message;
    private int status;
    private Long timestamp;
}

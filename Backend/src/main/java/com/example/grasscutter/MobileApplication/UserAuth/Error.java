package com.example.grasscutter.MobileApplication.UserAuth;

import lombok.Data;

@Data
public class Error {
    private String message;
    private int status;
    private Long timestamp;
}

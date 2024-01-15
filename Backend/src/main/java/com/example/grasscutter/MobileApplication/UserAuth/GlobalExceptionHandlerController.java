package com.example.grasscutter.MobileApplication.UserAuth;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import com.example.grasscutter.Auth.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
//import com.example.grasscutter.Auth.Error;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> validation(ValidationException ex, HttpServletRequest request) {
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
    }

}
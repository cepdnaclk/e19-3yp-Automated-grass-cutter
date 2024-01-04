package com.example.grasscutter.MobileApplication.Auth;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping(path = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto loginDto) {
        return ResponseEntity.ok(userService.signIn(loginDto));
    }

    @GetMapping(path = "/data")
    public ResponseEntity<?> data() {
        return ResponseEntity.ok(Arrays.asList("Hello world!"));
    }
}

package com.example.grasscutter.MobileApplication.User;


import java.util.List;

public interface UserService {
    SignUpResponseDto signUp(SignUpRequestDto signUpRequest);
    SignInResponseDto signIn(SignInRequestDto signInRequest);
    List<String> getAllDevicesForUser(String userId);
    User getUserById(String userId);
}

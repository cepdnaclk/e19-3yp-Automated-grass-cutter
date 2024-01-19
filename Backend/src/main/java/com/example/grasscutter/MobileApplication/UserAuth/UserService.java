package com.example.grasscutter.MobileApplication.UserAuth;


import java.util.List;

public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequest);

    SignInResponseDto signIn(SignInRequestDto signInRequest);

    List<String> getAllDevicesForUser(String userId);

}

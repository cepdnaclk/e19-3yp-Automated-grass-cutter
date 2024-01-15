package com.example.grasscutter.MobileApplication.UserAuth;


public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequest);

    SignInResponseDto signIn(SignInRequestDto signInRequest);

}

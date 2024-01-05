package com.example.grasscutter.MobileApplication.Auth;


public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequest);

    SignInResponseDto signIn(SignInRequestDto signInRequest);

}

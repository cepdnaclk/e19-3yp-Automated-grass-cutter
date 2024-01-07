package com.example.grasscutter.IoT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AccessTokenDecoder {

    public AccessTokenDecoder() {
        // private constructor to prevent instantiation
    }


    public static String getUsernameFromToken(String accessToken) {
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            return jwt.getClaim("username").asString();
            // Replace "username" with the actual claim name where the username is stored in your token.
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            return null;
        }
    }
}

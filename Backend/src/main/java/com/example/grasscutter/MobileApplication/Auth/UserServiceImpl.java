package com.example.grasscutter.MobileApplication.Auth;

import com.amazonaws.services.cognitoidp.model.*;

import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.MessageActionType;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;


    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequest) {

        SignUpResponseDto signUpResponse = new SignUpResponseDto();
        try {

            AttributeType emailAttr =
                    new AttributeType().withName("email").withValue(signUpRequest.getEmail());
            AttributeType emailVerifiedAttr =
                    new AttributeType().withName("email_verified").withValue("true");

            AdminCreateUserRequest userRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId)
                    .withUsername(signUpRequest.getEmail()).withTemporaryPassword(signUpRequest.getPassword())
                    .withUserAttributes(emailAttr, emailVerifiedAttr)
                    .withMessageAction(MessageActionType.SUPPRESS)
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);

            System.out.println("User " + createUserResult.getUser().getUsername()
                    + " is created. Status: " + createUserResult.getUser().getUserStatus());

            // Disable force change password during first login
            AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUsername(signUpRequest.getEmail()).withUserPoolId(userPoolId)
                    .withPassword(signUpRequest.getPassword()).withPermanent(true);

            cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
            signUpResponse.setStatusCode(0);
            signUpResponse.setStatusMessage("Successfully created user account.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Error during sign up : " + e.getMessage());
        }
        return signUpResponse;
    }


    private void confirmUser(String username) {
        List<AttributeType> attributesToUpdate = new ArrayList<>();
        attributesToUpdate.add(new AttributeType().withName("email_verified").withValue("true"));

        AdminUpdateUserAttributesRequest updateAttributesRequest = new AdminUpdateUserAttributesRequest()
                .withUsername(username)
                .withUserPoolId(userPoolId)
                .withUserAttributes(attributesToUpdate);

        cognitoClient.adminUpdateUserAttributes(updateAttributesRequest);
    }


    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequest) {
        SignInResponseDto signInResponse = new SignInResponseDto();

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInRequest.getEmail());
        authParams.put("PASSWORD", signInRequest.getPassword());

        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withClientId(clientId)
                .withUserPoolId(userPoolId)
                .withAuthParameters(authParams);

        try {
            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

            AuthenticationResultType authenticationResult;

            if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {
                System.out.println("Challenge Name is " + result.getChallengeName());
                throw new ValidationException("User has a challenge: " + result.getChallengeName());
            } else {
                System.out.println("User has no challenge");
                authenticationResult = result.getAuthenticationResult();

                signInResponse.setAccessToken(authenticationResult.getAccessToken());
                signInResponse.setIdToken(authenticationResult.getIdToken());
                signInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                signInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                signInResponse.setTokenType(authenticationResult.getTokenType());

                // Retrieve username from the IdToken's subject ('sub')
                DecodedJWT decodedJWT = JWT.decode(authenticationResult.getIdToken());
                String userId = decodedJWT.getSubject();
                signInResponse.setUserId(userId);

            }

        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        } finally {
            cognitoClient.shutdown();
        }

        return signInResponse;
    }


}

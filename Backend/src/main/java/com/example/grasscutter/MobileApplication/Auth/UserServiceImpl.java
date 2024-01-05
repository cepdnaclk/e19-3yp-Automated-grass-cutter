package com.example.grasscutter.MobileApplication.Auth;

import com.amazonaws.services.cognitoidp.model.*;

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
            AttributeType emailAttr = new AttributeType().withName("email").withValue(signUpRequest.getEmail());
            AttributeType passwordAttr = new AttributeType().withName("password").withValue(signUpRequest.getPassword());

            AdminCreateUserRequest userRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(signUpRequest.getEmail())
                    .withUserAttributes(emailAttr, passwordAttr)
                    .withMessageAction(MessageActionType.RESEND)
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);

            System.out.println("User " + createUserResult.getUser().getUsername()
                    + " is created. Status: " + createUserResult.getUser().getUserStatus());

            // Check the user's current status
            String userStatus = createUserResult.getUser().getUserStatus();

            // Verify if the user has already been verified
            boolean isEmailVerified = false;
            for (AttributeType attribute : createUserResult.getUser().getAttributes()) {
                if (attribute.getName().equals("email_verified") && attribute.getValue().equals("true")) {
                    isEmailVerified = true;
                    break;
                }
            }

            // If the user is not in FORCE_CHANGE_PASSWORD state and is not already verified, manually verify the email
            if (!userStatus.equals("FORCE_CHANGE_PASSWORD") && !isEmailVerified) {
                List<AttributeType> attributesToUpdate = new ArrayList<>();
                attributesToUpdate.add(new AttributeType().withName("email_verified").withValue("true"));

                AdminUpdateUserAttributesRequest updateAttributesRequest = new AdminUpdateUserAttributesRequest()
                        .withUsername(signUpRequest.getEmail())
                        .withUserPoolId(userPoolId)
                        .withUserAttributes(attributesToUpdate);

                cognitoClient.adminUpdateUserAttributes(updateAttributesRequest);
            }

            signUpResponse.setStatusCode(0);
            signUpResponse.setStatusMessage("Successfully created user account.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Error during sign up: " + e.getMessage());
        }
        return signUpResponse;
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequest) {
        SignInResponseDto signInResponse = new SignInResponseDto();

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", signInRequest.getEmail());
        authParams.put("PASSWORD", signInRequest.getPassword());

        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId)
                .withUserPoolId(userPoolId).withAuthParameters(authParams);

        try {
            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

            AuthenticationResultType authenticationResult = null;

            if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {

                System.out.println("Challenge Name is " + result.getChallengeName());

                if (result.getChallengeName().contentEquals("NEW_PASSWORD_REQUIRED")) {
                    if (signInRequest.getPassword() == null) {
                        throw new ValidationException("User must change password " + result.getChallengeName());

                    } else {

                        final Map<String, String> challengeResponses = new HashMap<>();
                        challengeResponses.put("USERNAME", signInRequest.getEmail());
                        challengeResponses.put("PASSWORD", signInRequest.getPassword());
                        // add new password
                        challengeResponses.put("NEW_PASSWORD", signInRequest.getNewPassword());

                        final AdminRespondToAuthChallengeRequest request =
                                new AdminRespondToAuthChallengeRequest()
                                        .withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                                        .withChallengeResponses(challengeResponses).withClientId(clientId)
                                        .withUserPoolId(userPoolId).withSession(result.getSession());

                        AdminRespondToAuthChallengeResult resultChallenge =
                                cognitoClient.adminRespondToAuthChallenge(request);
                        authenticationResult = resultChallenge.getAuthenticationResult();

                        signInResponse.setAccessToken(authenticationResult.getAccessToken());
                        signInResponse.setIdToken(authenticationResult.getIdToken());
                        signInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                        signInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                        signInResponse.setTokenType(authenticationResult.getTokenType());
                    }

                } else {
                    throw new ValidationException("User has other challenge " + result.getChallengeName());
                }
            } else {

                System.out.println("User has no challenge");
                authenticationResult = result.getAuthenticationResult();

                signInResponse.setAccessToken(authenticationResult.getAccessToken());
                signInResponse.setIdToken(authenticationResult.getIdToken());
                signInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                signInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                signInResponse.setTokenType(authenticationResult.getTokenType());
            }

        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        cognitoClient.shutdown();
        return signInResponse;
    }

}

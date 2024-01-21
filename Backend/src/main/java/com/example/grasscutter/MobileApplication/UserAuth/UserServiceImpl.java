package com.example.grasscutter.MobileApplication.UserAuth;

import com.amazonaws.services.cognitoidp.model.*;
//import javax.persistence.EntityNotFoundException; // Import for EntityNotFoundException

import com.example.grasscutter.IoT.AngleDistancePair;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import java.util.ArrayList;
import java.util.List;

import com.example.grasscutter.MobileApplication.DeviceAuth.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.MessageActionType;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Autowired
    private UserRepository userRepository;

    private MongoTemplate mongoTemplate;
    private DeviceRepository deviceRepository;


    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value(value = "${aws.cognito.clientId}")
    private String clientId;


    // Constructor injection of dependencies
    public UserServiceImpl(MongoTemplate mongoTemplate, DeviceRepository deviceRepository, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

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

            String userId = createUserResult.getUser().getUsername();

            System.out.println("User " + createUserResult.getUser().getUsername()
                    + " is created. Status: " + createUserResult.getUser().getUserStatus());

            // Disable force change password during first login
            AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUsername(signUpRequest.getEmail()).withUserPoolId(userPoolId)
                    .withPassword(signUpRequest.getPassword()).withPermanent(true);

            cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
            signUpResponse.setStatusCode(0);
            signUpResponse.setStatusMessage("Successfully created user account.");

            User user = new User(userId);
            //user.setId(userId);  // Save the user ID
            userRepository.save(user);

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
           // cognitoClient.shutdown();
        }
        return signInResponse;
    }


    public void addDeviceToUser(String userId, String deviceId) {
        try {
            // Step 1: Get the user from the UserRepository
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Step 2: Add the deviceId to the user's list of devices
            user.addDevice(deviceId); // Assuming User has a method like addDevice(String deviceId)

            // Step 3: Save the updated user back to the repository
            userRepository.save(user);
        } catch (Exception e) {
            // Handle any exceptions that might occur during the process
            e.printStackTrace();
            throw new RuntimeException("Error adding device to user: " + e.getMessage());
        }
    }

    public String getUserByDevice(String deviceId) {
        try {
            // Find the user who has the specified device in their list of devices
            Query query = new Query(Criteria.where("devices").in(deviceId));
            User user = mongoTemplate.findOne(query, User.class);

            // If user is found, return the user's ID
            if (user != null) {
                return user.getUserId();
            } else {
                // If no user is found with the specified device, return null
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during the process
            e.printStackTrace();
            throw new RuntimeException("Error getting user by device: " + e.getMessage());
        }
    }

    public void removeDeviceFromUser(String userId, String deviceId) {
        try {
            // Step 1: Get the user from the UserRepository
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Step 2: Remove the deviceId from the user's list of devices
            user.getDevices().remove(deviceId);

            // Step 3: Save the updated user back to the repository
            userRepository.save(user);
        } catch (Exception e) {
            // Handle any exceptions that might occur during the process
            e.printStackTrace();
            throw new RuntimeException("Error removing device from user: " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllDevicesForUser(String userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            return user.getDevices();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting devices for user: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(String userId) {
        // Implement the logic to retrieve a user by ID from your data source (e.g., database)
        // You might use userRepository.findById(userId) or a similar method
        return userRepository.findById(userId).orElse(null);
    }

    public void saveToUserMongo(String userId, String locationName, List<AngleDistancePair> data) {
        try {
            // Retrieve the existing User object from MongoDB (if it exists)
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // If the User object exists, update it with the new data for the specified location
            if (existingUser != null) {

                if (existingUser.getLocationData() == null) {
                    existingUser.setLocationData(new HashMap<>());
                }
                existingUser.addDataForLocation(locationName, data);

                // Save the updated User object back to MongoDB
                userRepository.save(existingUser);

                System.out.println("Updated and saved existing User in MongoDB: " + data.toString() + " for User ID: " + userId + ", Location: " + locationName);
            } else {
                // Handle the case where the User object doesn't exist
                System.out.println("User does not exist in MongoDB for ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            System.err.println("Failed to save/update User in MongoDB");
        }
    }




}

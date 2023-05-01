package com.example.facepay.FacePay.Service;

import com.example.facepay.FacePay.Model.Topup;
import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Repository.UserRepository;
import com.example.facepay.FacePay.Response.UIResponse;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UIService {

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    String connectionString = "mongodb+srv://usn012y2018:facepay1@facepay.y1chyja.mongodb.net/test";
    ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    public UIResponse register(User user) {
        log.info("Insider Register");
        User userCheck = userRepository.findByEmail(user.getEmail());
        if (userCheck != null) {
            log.warn("User already exists.");
            return new UIResponse(-1, "Email ID already exists for a user.");
        }
        User userResponse = userRepository.save(user);
        UIResponse response = null;
        if (userResponse != null) {
            log.info("New user created");
            response = new UIResponse(1, "Successfully added a new user.");
        } else {
            log.warn("failed to create a new user");
            response = new UIResponse(-1, "Failed to Create a new user.");
        }
        log.info("Exiting register function.");
        return response;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<UIResponse> login(User userLogin) {

        log.info("Entered login function");
        User user = userRepository.findByEmail(userLogin.getEmail());
        ResponseEntity<UIResponse> response = null;
        if (user != null) {
            if (user.getPassword().equals(userLogin.getPassword())) {
                log.info("Login successful");
                response = new ResponseEntity<>(new UIResponse(1, "Login Successful"), HttpStatus.OK);
            } else {
                log.warn("Incorrect Password");
                response = new ResponseEntity<>(new UIResponse(-1, "Incorrect Password"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            log.warn("User not found");
            response = new ResponseEntity<>(new UIResponse(-1, "User not found"), HttpStatus.NOT_FOUND);
        }
        log.info("Exiting login function");
        return response;
    }

    public ResponseEntity<UIResponse> topup(Topup topup) {

        log.info("Entering updateBal function");
        User user = userRepository.findByUserID(topup.getUserID());
        if (user != null) {
            log.info("Updating Bal");
            user.setBalance(user.getBalance() + topup.getAmount());
            userRepository.save(user);
            log.info("Exiting updateBal");
            return new ResponseEntity<>(new UIResponse(1, "Balance updated Successfully"), HttpStatus.OK);
        } else {
            log.error("User not found");
            log.info("Exiting updateBal");
            return new ResponseEntity<>(new UIResponse(-1, "User Not Found."), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getDetails(String email) {
        log.info("Entering getDetails");
        User user = userRepository.findByEmail(email);
        User response = null;
        if(user != null) {
            log.info("User found");
            response = new User();
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setUserName(user.getUserName());
            response.setPhone(user.getPhone());
            response.setUserID(user.getUserID());
            response.setBalance(user.getBalance());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            log.warn("User does not exist");
            log.info("Exiting getDetails");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.facepay.FacePay.Service;

import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Repository.UserRepository;
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

    public String register(User user) {

        User userResponse = userRepository.save(user);
        return userResponse.getUserID();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<String> login(User userLogin) {

        User user = userRepository.findByEmail(userLogin.getEmail());
        ResponseEntity<String> response = null;
        if(user != null) {
            if(user.getPassword().equals(userLogin.getPassword()))
                response = new ResponseEntity<>("success", HttpStatus.OK);
            else
                response = new ResponseEntity<>("Password Incorrect", HttpStatus.UNAUTHORIZED);
        }
        else {
            response = new ResponseEntity<>("email not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }
}

package com.example.facepay.FacePay.Controller;

import com.example.facepay.FacePay.Model.Topup;
import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Service.UIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facepay")
@Slf4j
public class UIController {

    @Autowired
    UIService uiService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String userID = uiService.register(user);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(userID, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return uiService.login(user);
    }

    @PostMapping("/updateBal")
    public ResponseEntity<String> topup(@RequestBody Topup topup) {
        return uiService.topup(topup);
    }



}

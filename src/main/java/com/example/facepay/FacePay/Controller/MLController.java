package com.example.facepay.FacePay.Controller;

import com.example.facepay.FacePay.Service.MLService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class MLController {

    @Autowired
    @GetMapping(value="/")
    public String Test() {
        String test = "Hello Server!!";
        return test;
    }

    @Autowired
    private MLService mlService;
    @PostMapping(value = "/trainUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity trainUser(@RequestParam("userID") String userID) {
        
        return mlService.trainUserData(userID);

    }
}

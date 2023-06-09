package com.example.facepay.FacePay.Controller;

import com.example.facepay.FacePay.Model.Topup;
import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Response.UIResponse;
import com.example.facepay.FacePay.Service.UIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/facepay")
@Slf4j
@CrossOrigin
public class UIController {

    @Autowired
    UIService uiService;

    @PostMapping("/register")
    @CrossOrigin
    public ResponseEntity<UIResponse> register(@RequestBody User user) {
        UIResponse response = uiService.register(user);
        ResponseEntity<UIResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<UIResponse> login(@RequestBody User user) {
        return uiService.login(user);
    }

    @PostMapping("/updateBal")
    public ResponseEntity<UIResponse> topup(@RequestBody Topup topup) {
        return uiService.updateBal(topup);
    }

    @GetMapping("/details")
    public ResponseEntity<User> getDetails(@RequestParam(value = "email") String email) {
        return uiService.getDetails(email);
    }

}

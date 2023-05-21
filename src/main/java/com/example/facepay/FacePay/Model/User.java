package com.example.facepay.FacePay.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    String userID;
    String fullName;
    String email;
    String userName;
    String phone;
    String password;
    double balance;
    Integer flag;
    Integer status;


}

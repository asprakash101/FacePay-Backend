package com.example.facepay.FacePay.Repository;

import com.example.facepay.FacePay.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByEmail(String email);

    User findByUserID(String userID);
}

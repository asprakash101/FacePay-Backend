package com.example.facepay.FacePay.Repository;

import com.example.facepay.FacePay.Model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, Long> {
}

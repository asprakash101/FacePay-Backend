package com.example.facepay.FacePay.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collection = "images")
@Data
public class Image {

    MultipartFile img;
    @Id
    String user_id;


    public Image(MultipartFile img, String user_id) {
        this.img = img;
        this.user_id = user_id;
    }


}

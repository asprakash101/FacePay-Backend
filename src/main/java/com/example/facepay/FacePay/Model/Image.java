package com.example.facepay.FacePay.Model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collation = "images")
@Data
public class Image {

    @Id
    long id;
    MultipartFile img;
    String user_id;


    public Image(MultipartFile img, String user_id) {
        this.img = img;
        this.user_id = user_id;
    }


}

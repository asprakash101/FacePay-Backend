package com.example.facepay.FacePay.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MLService {

    final private String MLServerURL = new String("http://127.0.0.1:5000");

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    String connectionString = "mongodb+srv://usn012y2018:facepay1@facepay.y1chyja.mongodb.net/test";
    ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();


    public ResponseEntity trainUserData(String userID) {

        ResponseEntity response = null;
        // response = new ResponseEntity("Successfully Trained User Data", HttpStatus.OK);
        // return response;
        String reqBody = "{\"user_id\":\""+userID+"\"}";
        String result = restTemplate.postForObject(MLServerURL + "/train-server/train?user_id="+userID,"",String.class);
// //        String result = restTemplate.getForObject("http://10.71.33.242:9034/nps/rest/customfiles/config", String.class);
// //        String result = new String("{\"name\":\"John\"}");
//         JSONObject jsonResponse = null;
        return new ResponseEntity(result, HttpStatus.OK);
//         try {
//             jsonResponse = new JSONObject(result);
//             String value = jsonResponse.getString("name");
//             if(result != null && result.equalsIgnoreCase("success")){
//                 response = new ResponseEntity("Successfully Trained User Data", HttpStatus.OK);
//             }
//             else {
//                 response = new ResponseEntity("Failed To Train User Data Into The System", HttpStatus.OK);
//             }
//         } catch (JSONException e) {
//             log.error("Error while converting user .pkl data to JSON: " + e.getMessage());
//             response = new ResponseEntity("Failed To Train User Data Into The System", HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//         finally {

//             return response;

//         }

    }
}

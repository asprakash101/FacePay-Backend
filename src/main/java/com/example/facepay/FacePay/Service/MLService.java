package com.example.facepay.FacePay.Service;

import com.example.facepay.FacePay.Model.Topup;
import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Repository.UserRepository;
import com.example.facepay.FacePay.Utility.MLUtil;
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

    final private String MLServerURL = new String("http://192.168.137.143:5000");
    final private Double minBalance = 50.0;

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UIService uiService;

    @Autowired
    MLUtil mlUtil;

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

    public ResponseEntity<User> validate(String userID, Integer stationID) throws Exception {

        User user = userRepository.findByUserID(userID);
        Integer flag = null;
        Double balance = null;

        Topup topup = null;
        int fare = 0;
        if(user!=null){
            balance = user.getBalance();
            if(balance < minBalance){
                log.error("Balance is below minimum balance");
                throw new Exception("Minimum Balance Exception");
            }
            flag = user.getFlag();
            if(flag == null || flag == 0) {
                log.info("User " + userID+" entry at station: " + stationID + " with balance: " + user.getBalance());
                user.setFlag(stationID);
            }
            else {

                fare = mlUtil.calcFare(flag, stationID);
                topup = new Topup(user.getUserID(),user.getPhone(),fare*-1);
                user = mlUtil.updateBal(topup);
                user.setFlag(null);
                log.info("User " + userID+" exit at station: " + stationID + " with balance: " + user.getBalance());
            }
            User response = userRepository.save(user);
            response = userRepository.findByUserID(user.getUserID());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            log.error("User not found");
            throw new Exception("User not found");
        }
    }
}

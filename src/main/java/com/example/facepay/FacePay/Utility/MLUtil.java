package com.example.facepay.FacePay.Utility;

import com.example.facepay.FacePay.Model.Topup;
import com.example.facepay.FacePay.Model.User;
import com.example.facepay.FacePay.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MLUtil {

    @Autowired
    UserRepository userRepository;

    public int calcFare(Integer flag, Integer stationID) {

//        return 35;
        int baseFare = 10;
        int totalFare = 0;
        totalFare = (flag - stationID)*5;
        if(totalFare<0){
            totalFare = totalFare*-1;
        }

        return totalFare + baseFare;
    }

    public User updateBal(Topup topup) {
        User user = userRepository.findByUserID(topup.getUserID());
        if (user != null) {
            log.info("Updating Bal");
            user.setBalance(user.getBalance() + topup.getAmount());
            log.info("Exiting updateBal");
        } else {
            log.error("User not found");
            log.info("Exiting updateBal");
        }
        return userRepository.save(user);
    }
}

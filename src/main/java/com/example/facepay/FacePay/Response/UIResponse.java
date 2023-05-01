package com.example.facepay.FacePay.Response;

import lombok.Data;

@Data
public class UIResponse {

    private Integer status;
    private String message;

    public UIResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}

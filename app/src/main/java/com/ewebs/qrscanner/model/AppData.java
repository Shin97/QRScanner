package com.ewebs.qrscanner.model;

/**
 * Created by PartnerPC on 2016/9/20.
 */
public class AppData {
    private String token,phone;

    public AppData(String token, String phone) {
        this.token = token;
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }
}

package com.example.express_delivery;

import org.litepal.crud.DataSupport;

public class delivery extends DataSupport{
    private String deliveryNum;
    private String location;
    private String phoneNum;
    private int RandomCode;

    public String getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getRandomCode() {
        return RandomCode;
    }

    public void setRandomCode(int randomCode) {
        RandomCode = randomCode;
    }
}

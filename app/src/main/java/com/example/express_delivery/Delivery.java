package com.example.express_delivery;

import org.litepal.crud.DataSupport;

public class Delivery extends DataSupport{
    private String deliveryNum;
    private String location;
    private int RandomCode;

    public String getdeliveryNum() {
        return deliveryNum;
    }

    public void setdeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRandomCode() {
        return RandomCode;
    }

    public void setRandomCode(int randomCode) {
        RandomCode = randomCode;
    }
}

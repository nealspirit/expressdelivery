package com.example.express_delivery;

import java.util.Random;

public class Utility {
    public static String location(int position){
        String loc = null;
        switch (position){
            case 0:
                loc = "01 01";
                break;
            case 1:
                loc = "01 02";
                break;
            case 2:
                loc = "01 03";
                break;
            case 3:
                loc = "02 01";
                break;
            case 4:
                loc = "02 02";
                break;
            case 5:
                loc = "02 03";
                break;
            default:
                break;
        }
        return loc;
    }

    public static String location_string(int position){
        String loc_string = null;
        switch (position){
            case 0:
                loc_string = "01行01列";
                break;
            case 1:
                loc_string = "01行02列";
                break;
            case 2:
                loc_string = "01行03列";
                break;
            case 3:
                loc_string = "02行01列";
                break;
            case 4:
                loc_string = "02行02列";
                break;
            case 5:
                loc_string = "02行03列";
                break;
            default:
                break;
        }
        return loc_string;
    }

    public static int initcode() {
        Random random = new Random();
        int code = random.nextInt(9999);
        return code;
    }
}

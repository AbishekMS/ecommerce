package com.example.ecommerce.utils;

import java.util.Date;

public class DateUtils {
    private DateUtils(){

    }

    public static Date now(){
        return new Date(System.currentTimeMillis());
    }

    public static Date future(){
        return new Date(System.currentTimeMillis()+ 60*60*30);
    }
}

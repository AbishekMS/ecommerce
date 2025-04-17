package com.example.ecommerce.utils;

public class ValidateUtils {
    private ValidateUtils(){

    }
    public static boolean isValidString(String str){
        return str==null && str.isEmpty();
    }
}

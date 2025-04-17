package com.example.ecommerce.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    private static final DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ResponseUtil(){

    }
    public static ResponseEntity<Map<String,Object>> errorResponse(Exception ex, HttpServletRequest request, String errorType, HttpStatus status){
        Map<String,Object> response=new HashMap<>();
        String timing= LocalDateTime.now().format(format);
        response.put("timestramp: ",  timing);
        response.put("message: ",ex.getMessage());
        response.put("error: ", errorType);
        response.put("status: ", status.value());
        response.put("path: ",request.getRequestURI());
        return new ResponseEntity<>(response,status);
    }
}

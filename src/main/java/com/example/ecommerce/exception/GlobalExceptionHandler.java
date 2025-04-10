package com.example.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> runTimeExceptionHandler(RuntimeException msg, HttpServletRequest request){
        Map<String,Object> response= new HashMap<>();
        String timing= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.put("timestamp: ", timing);
        response.put("message: ", msg.getMessage());
        response.put("error: ","Runtime Exception");
        response.put("status: ", HttpStatus.BAD_REQUEST.value());
        response.put("path: ", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InvalidProductId.class)
    public ResponseEntity<Map<String,Object>> productIdNotFoundExceptionHandler(Exception msg, HttpServletRequest request){
        Map<String,Object> response= new HashMap<>();
        String timing= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.put("timestamp: ", timing);
        response.put("message: ", msg.getMessage());
        response.put("status: ", HttpStatus.BAD_REQUEST.value());
        response.put("path: ", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String,Object>> InsufficentStockExceptionHandler(Exception msg, HttpServletRequest request){
        Map<String,Object> response= new HashMap<>();
        String timing= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.put("timestamp: ", timing);
        response.put("message: ", msg.getMessage());
        response.put("status: ", HttpStatus.BAD_REQUEST.value());
        response.put("path: ", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}

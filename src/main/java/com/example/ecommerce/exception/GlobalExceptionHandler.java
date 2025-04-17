package com.example.ecommerce.exception;

import com.example.ecommerce.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> runTimeExceptionHandler(RuntimeException ex, HttpServletRequest request){
        HttpStatus status= HttpStatus.BAD_REQUEST;
        String error= "Runtime Exception";

        if (ex instanceof org.springframework.security.access.AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            error= "Forbidden";
        }
        return ResponseUtil.errorResponse(ex,request,error, status);
    }

    @ExceptionHandler(InvalidProductId.class)
    public ResponseEntity<Map<String,Object>> productIdNotFoundExceptionHandler(Exception ex, HttpServletRequest request){
        return ResponseUtil.errorResponse(ex,request,"Invalid ProductId Exception", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String,Object>> inSufficentStockExceptionHandler(Exception ex, HttpServletRequest request){
        return ResponseUtil.errorResponse(ex,request,"Insufficient Stock Exception", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> illegalArugementExceptionHandler(Exception ex, HttpServletRequest request){
        return ResponseUtil.errorResponse(ex,request,"Illegal Argument Exception",HttpStatus.BAD_REQUEST);
    }
}

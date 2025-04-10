package com.example.ecommerce.exception;


public class InvalidProductId extends RuntimeException{
    public InvalidProductId(Long productId){
        super("Product with Id: "+productId+" is not available in inventory");
    }
}

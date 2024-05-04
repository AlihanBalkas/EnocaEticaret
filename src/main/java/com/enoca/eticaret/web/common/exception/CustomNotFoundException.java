package com.enoca.eticaret.web.common.exception;

public class CustomNotFoundException extends RuntimeException{

    public CustomNotFoundException(String message){
        super(message);
    }
}
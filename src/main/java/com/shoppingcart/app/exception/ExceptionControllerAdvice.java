package com.shoppingcart.app.exception;

import com.shoppingcart.app.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequestException e){

        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");
        return  new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CartNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Cart Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ItemNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Item Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
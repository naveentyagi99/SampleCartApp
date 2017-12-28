package com.shoppingcart.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cart not found")
public class CartNotFoundException extends ResourceNotFoundException {

    public CartNotFoundException(String message) {
        super(message);
    }

}

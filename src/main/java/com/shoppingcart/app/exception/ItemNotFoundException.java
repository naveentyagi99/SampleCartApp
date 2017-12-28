package com.shoppingcart.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item not found")
public class ItemNotFoundException extends ResourceNotFoundException {

    public ItemNotFoundException(String message) {
        super(message);
    }

}

package com.shoppingcart.app.controller;

import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/shopping_carts", produces = "application/json",
        consumes = "application/json")
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCart(@RequestBody Cart cart) throws BadRequestException{
        cartService.add(cart);
        return new ResponseEntity<Cart>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{cart_id}", method = RequestMethod.GET)
    public ResponseEntity findCart(@PathVariable("cart_id")int cart_id) throws CartNotFoundException{
        return new ResponseEntity<Cart>(cartService.findById(cart_id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> findCarts(){
        return new ResponseEntity<List<Cart>>(cartService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{cart_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCart(@PathVariable("cart_id")int cart_id) throws CartNotFoundException{
        cartService.deleteById(cart_id);
        return new ResponseEntity<Cart>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateCart(@RequestBody Cart cart) throws CartNotFoundException{
        return new ResponseEntity<Cart>(cartService.updateCart(cart), HttpStatus.CREATED);
    }
}

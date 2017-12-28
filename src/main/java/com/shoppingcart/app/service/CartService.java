package com.shoppingcart.app.service;

import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.model.Item;

import java.util.List;

public interface CartService {

    int add(Cart cart) throws BadRequestException;

    int deleteById(int id) throws CartNotFoundException;

    Cart updateCart(Cart cart) throws CartNotFoundException;

    Cart findById(int id) throws CartNotFoundException;

    List<Cart> findAll();

    List<Item> findCartItems(int id) throws CartNotFoundException;

}

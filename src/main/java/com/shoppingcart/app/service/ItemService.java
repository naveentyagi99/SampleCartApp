package com.shoppingcart.app.service;


import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.ItemNotFoundException;
import com.shoppingcart.app.model.Item;

public interface ItemService {

     int add(Item item) throws BadRequestException;

     int deleteById(int id) throws ItemNotFoundException;

     Item updateItem(Item item) throws ItemNotFoundException;

     Item findById(int id, int cart_Id) throws ItemNotFoundException;

}
package com.shoppingcart.app.controller;

import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.ItemNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.model.Item;
import com.shoppingcart.app.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shopping_carts/{cart_Id}", produces = "application/json",
        consumes = "application/json")
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity createItem( @PathVariable("cart_Id") int cart_Id, @RequestBody Item item) throws BadRequestException{
        itemService.add(item);
        return new ResponseEntity<Item>(HttpStatus.CREATED);
    }

   @RequestMapping(value = "items/{cart_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCart(@PathVariable("cart_Id") int cart_Id, @PathVariable("cart_id")int cart_id) throws ItemNotFoundException {
        itemService.deleteById(cart_id);
        return new ResponseEntity<Cart>(HttpStatus.OK);
    }

     @RequestMapping(value = "/items", method = RequestMethod.PUT)
    public ResponseEntity updateItem(@PathVariable("cart_Id") int cart_Id, @RequestBody Item item) throws ItemNotFoundException{
        return new ResponseEntity<Item>(itemService.updateItem(item), HttpStatus.CREATED);
    }

    @RequestMapping(value = "items/{item_Id}", method = RequestMethod.GET)
    public ResponseEntity getItem(@PathVariable("item_Id") int item_Id, @PathVariable("cart_Id") int cart_Id) throws ItemNotFoundException{

        return new ResponseEntity<Item>(itemService.findById(item_Id, cart_Id), HttpStatus.OK);
    }
}

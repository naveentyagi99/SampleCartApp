package com.shoppingcart.app.dao;


import com.shoppingcart.app.model.Item;

public interface ItemDao {

     int add(Item item) ;

     Item findById(int id, int cart_Id);

     int deleteById(int id);

     int updateItem(Item item);

}
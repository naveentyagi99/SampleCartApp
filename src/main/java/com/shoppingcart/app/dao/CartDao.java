package com.shoppingcart.app.dao;


import com.shoppingcart.app.model.Cart;

import java.util.List;

public interface CartDao {

     int add(Cart cart) ;

     int deleteById(int id);

     List<Cart> findAll();

     Cart findById(int id) ;

     int updateCart(Cart cart);

}
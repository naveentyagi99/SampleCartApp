package com.shoppingcart.app.service;

import com.shoppingcart.app.dao.CartDao;
import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;

    public int add(Cart cart) throws BadRequestException {
        try {
            return cartDao.add(cart);
        }
        catch (Exception ex){
            throw new BadRequestException(" Bad Request");
        }
    }

    public int deleteById(int id) throws CartNotFoundException{
        int count = cartDao.deleteById(id);
        if(count<1){
            throw new CartNotFoundException("Cart Not Found");
        }
        return count;
    }

    public Cart updateCart(Cart cart) throws CartNotFoundException{
        int count = cartDao.updateCart(cart);
        if(count<1){
            throw  new CartNotFoundException("Cart Not Found");
        }
        return findById(cart.getId());
    }

    public Cart findById(int id) throws CartNotFoundException {
        Cart cart = cartDao.findById(id);
        if(cart == null){
            throw new CartNotFoundException("Cart Not Found");
        }
        return cart;
    }

    public List<Cart> findAll(){
        return cartDao.findAll();
    }

    public List<Item> findCartItems(int id) throws CartNotFoundException{
       return null;
    }
}

package com.shoppingcart.app.service;


import com.shoppingcart.app.dao.CartDaoImpl;
import com.shoppingcart.app.dao.EntityDaoImplTest;
import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.model.Cart;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


public class CartServiceImplUnitTest extends EntityDaoImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartDaoImpl cartDao;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_BlankName_ShouldThrowBadRequestException()
        throws BadRequestException{

        Cart cart = getCart();
        cart.setName("");

        when(cartDao.add(cart)).thenThrow(DataIntegrityViolationException.class);
        cartService.add(cart);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewCart_NullName_ShouldThrowBadRequestException()
            throws BadRequestException {
        Cart cart = getCart();
        cart.setName(null);

        when(cartDao.add(cart)).thenThrow(DataIntegrityViolationException.class);
        cartService.add(cart);
    }
    @Test(expected = BadRequestException.class)
    public void add_NewCart_ZeroCartId_ShouldThrowBadRequestException()
            throws BadRequestException{

        Cart cart = getCart();
        cart.setId(0);
        when(cartDao.add(cart)).thenThrow(DataIntegrityViolationException.class);
        cartService.add(cart);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewCart_NegativeCartId_ShouldBadRequestException()
                    throws BadRequestException{
        Cart cart = getCart();
        cart.setId(-1);
        when(cartDao.add(cart)).thenThrow(DataIntegrityViolationException.class);
        cartService.add(cart);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewCart_DuplicateCartId_ShouldThrowBadRequestException()
                throws  BadRequestException{
        Cart cart = getCart();
        cart.setId(1);

        when(cartDao.add(cart)).thenThrow(DuplicateKeyException.class);
        cartService.add(cart);
    }

    @Test
    public void add_NewCart_NullDName_ShouldAddCart()
        throws BadRequestException{

        Cart cart = getCart();
        cart.setName(null);

        when(cartDao.add(cart)).thenReturn(1);
        cartService.add(cart);
    }

    @Test
    public void add_NewCart_BlankName_ShouldAddCart() throws BadRequestException{

        Cart cart = getCart();
        cart.setName("");

        when(cartDao.add(cart)).thenReturn(1);
        cartService.add(cart);
    }

    @Test
    public void add_NewCart_ShouldAddCart() throws BadRequestException{

        Cart cart = getCart();

        when(cartDao.add(cart)).thenReturn(1);
        cartService.add(cart);
    }

    @Test(expected = CartNotFoundException.class)
    public void deleteById_CartNotFound_ShouldThrow_CartNotFoundException()
            throws CartNotFoundException {

        when(cartDao.deleteById(anyInt())).thenReturn(0);
        int count = cartService.deleteById(1001);

    }

    @Test(expected = CartNotFoundException.class)
    public void update_CartNotFound_ShouldThrow_CartNotFoundException()
            throws CartNotFoundException {

        Cart cart = getCart();
        when(cartDao.updateCart(cart)).thenReturn(0);
        cartService.updateCart(cart);
    }

    public void update_CartFound_ShouldReturnUpdatedCart()
            throws CartNotFoundException {

        Cart cart = getCart();

        Cart updated = getCart();
        updated.setName("Updated");

        when(cartDao.updateCart(cart)).thenReturn(1);
        when(cartDao.findById(anyInt())).thenReturn(cart);
        Cart actual  = cartService.updateCart(cart);

        assertNotNull(actual);
        assertEquals(actual, updated);
    }

    @Test
    public void deleteById_CartFound_ShouldReturnCount()
            throws CartNotFoundException {

        when(cartDao.deleteById(anyInt())).thenReturn(1);

        int count = cartService.deleteById(1);
        assertEquals(1, count);
    }



    @Test(expected = CartNotFoundException.class)
    public void findById_CartNotFound_ShouldRThrowCartNotFoundException() throws Exception {

        when(cartDao.findById(anyInt())).thenThrow(CartNotFoundException.class);
        Cart cart = cartService.findById(101);
    }


    @Test
    public void findById_CartFound_ShouldReturnFoundCart() throws Exception {

        Cart actual = getCart();
        when(cartDao.findById(anyInt())).thenReturn(actual);

        Cart  cart = cartService.findById(1);
        assertNotNull(cart);
        assertEquals(actual, cart);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(cartDao,times(1)).findById(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());

    }

    private Cart getCart(){
        Cart cart = new Cart();
        cart.setId(100);
        cart.setName("Cart");
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());

        return cart;
    }
}
package com.shoppingcart.app.controller;


import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.service.CartService;
import com.shoppingcart.app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CartControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    CartService cartService;

    @InjectMocks
    CartController cartController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
                .build();
    }

    @Test
    public void add_NewCart_BlankName_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Cart cart = getCart();
        cart.setName("");

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertEquals("", varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void add_NewCart_NullName_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Cart cart = getCart();
        cart.setName(null);

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertNull(varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }


    @Test
    public void add_NewCart_ZeroId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Cart cart = getCart();
        cart.setId(0);

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getId());
        assertEquals("Cart",varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }


    @Test
    public void add_NewCart_NegativeId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Cart cart = getCart();
        cart.setId(-1);

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getId());
        assertEquals("Cart",varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void add_NewCart_ShouldCreateCart_WithStatusCodeCreated()
            throws Exception{

        Cart cart = getCart();

        when(cartService.add(any(Cart.class))).thenReturn(1);

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertEquals("Cart",varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }


    @Test
    public void add_NewCart_DuplicateId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Cart cart = getCart();
        cart.setId(1);

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(cart)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).add(varArgs.capture());

        assertEquals(1, varArgs.getValue().getId());
        assertEquals("Cart",varArgs.getValue().getName());

        verify(cartService).add(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void findById_CartFound_ShouldReturnFoundCart() throws Exception {

        when(cartService.findById(anyInt())).thenReturn(getCart());
        mockMvc.perform(get("/shopping_carts/{id}", 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$id", is(100)))
                .andExpect(jsonPath("$name", is("Cart")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(cartService).findById(varArgs.capture());

        assertEquals(100, (int)varArgs.getValue());

        verify(cartService).findById(100);
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void findById_CartNotFound_ShouldReturnNotFoundStatusCode() throws Exception {

        when(cartService.findById(anyInt())).thenThrow(CartNotFoundException.class);
        mockMvc.perform(get("/shopping_carts/{id}", 11)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(cartService).findById(varArgs.capture());

        assertEquals(11, (int)varArgs.getValue());

        verify(cartService).findById(11);
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void deleteById_CartFound_ShouldDeleteCartReturnStatusOk() throws Exception {

        when(cartService.deleteById(anyInt())).thenReturn(1);
        mockMvc.perform(delete("/shopping_carts/{id}", 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(cartService).deleteById(varArgs.capture());

        assertEquals(100, (int)varArgs.getValue());

        verify(cartService).deleteById(100);
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void deleteById_CartNotFound_ShouldReturnNotFound() throws Exception {

        when(cartService.deleteById(anyInt())).thenThrow(CartNotFoundException.class);

        mockMvc.perform(delete("/shopping_carts/{id}", 111)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(cartService).deleteById(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(cartService).deleteById(111);
        verifyNoMoreInteractions(cartService);
    }

    @Test
    public void update_CartNotFound_ShouldReturnNotFound() throws Exception {


        String clientJson = "{\n" +
                "    \"id\": 11,\n" +
                "    \"name\": \"Test \",\n" +
                "    \"createdAt\": \"2017-12-24 09:49:02\",\n" +
                "    \"updatedAt\": \"2017-12-24 09:49:02\"\n" +
                "}";

        when(cartService.updateCart(any(Cart.class))).thenThrow(new CartNotFoundException("Bad Request"));

        mockMvc.perform(put("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Cart> varArgs = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).updateCart(varArgs.capture());

        assertEquals(11, varArgs.getValue().getId());

        verify(cartService).updateCart(any(Cart.class));
        verifyNoMoreInteractions(cartService);
    }

    private Cart getCart(){
        Cart cart = new Cart();

        cart.setName("Cart");
        cart.setId(100);
        cart.setUpdatedAt(new Date());
        cart.setCreatedAt(new Date());

        return cart;
    }
}

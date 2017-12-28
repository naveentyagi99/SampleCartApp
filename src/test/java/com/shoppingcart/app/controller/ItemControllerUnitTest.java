package com.shoppingcart.app.controller;


import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.CartNotFoundException;
import com.shoppingcart.app.exception.ItemNotFoundException;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.model.Item;
import com.shoppingcart.app.service.CartService;
import com.shoppingcart.app.service.ItemService;
import com.shoppingcart.app.util.TestUtil;
import javassist.NotFoundException;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ItemControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    ItemService itemService;

    @InjectMocks
    ItemController itemController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }

    @Test
    public void add_NewItem_BlankDescription_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Item item = getItem();
        item.setDescription("");

        when(itemService.add(any(Item.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertEquals("", varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void add_NewItem_NullDescription_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Item item = getItem();
        item.setDescription(null);

        when(itemService.add(any(Item.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertNull(varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }


    @Test
    public void add_NewItem_ZeroId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Item item = getItem();
       item.setId(0);

        when(itemService.add(any(Item.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getId());
        assertEquals("Item",varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }


    @Test
    public void add_NewItem_NegativeId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Item item = getItem();
        item.setId(-1);

        when(itemService.add(any(Item.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getId());
        assertEquals("Item",varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void add_NewItem_ShouldCreateItem_WithStatusCodeCreated()
            throws Exception{

        Item item = getItem();

        when(itemService.add(any(Item.class))).thenReturn(1);

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getId());
        assertEquals("Item",varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }


    @Test
    public void add_NewItem_DuplicateId_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Item item = getItem();
        item.setId(1);

        when(itemService.add(any(Item.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts/{cart_Id}/items",100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Item> varArgs = ArgumentCaptor.forClass(Item.class);
        verify(itemService).add(varArgs.capture());

        assertEquals(1, varArgs.getValue().getId());
        assertEquals("Item",varArgs.getValue().getDescription());

        verify(itemService).add(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void findById_ItemFound_ShouldReturnFoundItem() throws Exception {

        when(itemService.findById(anyInt(), anyInt())).thenReturn(getItem());
        mockMvc.perform(get("/shopping_carts/{cart_Id}/items/{itemId}", 100, 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$id", is(100)))
                .andExpect(jsonPath("$description", is("Item")));


        verify(itemService).findById(100, 100);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void findById_ItemNotFound_ShouldReturnNotFoundStatusCode() throws Exception {

        when(itemService.findById(anyInt(), anyInt())).thenThrow(ItemNotFoundException.class);
        mockMvc.perform(get("/shopping_carts/{cart_Id}/items/{itemId}", 100,100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(itemService).findById(100, 100);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void deleteById_ItemFound_ShouldDeleteItemReturnStatusOk() throws Exception {

        when(itemService.deleteById(anyInt())).thenReturn(1);
        mockMvc.perform(delete("/shopping_carts/{cart_Id}/items/{itemId}", 100,100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(itemService).deleteById(100);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void deleteById_ItemNotFound_ShouldReturnNotFound() throws Exception {

        when(itemService.deleteById(anyInt())).thenThrow(ItemNotFoundException.class);

        mockMvc.perform(delete("/shopping_carts/{cart_Id}/items/{itemId}", 100, 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(itemService).deleteById(100);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    public void update_ItemNotFound_ShouldReturnNotFound() throws Exception {


        String clientJson = "{\n" +
                "    \"id\": 101,\n" +
                "    \"description\": \"This is a Test\",\n" +
                "    \"createdAt\": \"2017-12-24 09:49:02\",\n" +
                "    \"updatedAt\": \"2017-12-24 09:49:02\",\n" +
                "    \"shoppingCartId\": 1\n" +
                "}";

        when(itemService.updateItem(any(Item.class))).thenThrow(new ItemNotFoundException("Bad Request"));

        mockMvc.perform(put("/shopping_carts/{cart_Id}/items", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(itemService).updateItem(any(Item.class));
        verifyNoMoreInteractions(itemService);
    }

    private Item getItem(){
        Item item = new Item();

        item.setDescription("Item");
        item.setId(100);
        item.setUpdatedAt(new Date());
        item.setCreatedAt(new Date());

        return item;
    }
}

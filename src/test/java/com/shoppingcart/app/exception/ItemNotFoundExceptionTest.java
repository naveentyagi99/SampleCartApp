package com.shoppingcart.app.exception;

import com.shoppingcart.app.controller.CartController;
import com.shoppingcart.app.controller.ItemController;
import com.shoppingcart.app.service.CartService;
import com.shoppingcart.app.service.ItemService;
import com.shoppingcart.app.util.AdviceHandlerExceptionResolver;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemNotFoundExceptionTest {

    private MockMvc mockMvc;

    @Mock
    ItemService itemService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(itemService)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void findItem_ItemNotFound_ShouldReturnNotFoundStatusCode_AndErrorMessage_ItemtNotFound() throws  Exception{

        when(itemService.findById(anyInt(),anyInt())).thenThrow(ItemNotFoundException.class);

        mockMvc.perform(get("/shopping_carts/{cartId}/items/{itemId}", 1001, 1002)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

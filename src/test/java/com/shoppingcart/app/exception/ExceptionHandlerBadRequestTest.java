package com.shoppingcart.app.exception;

import com.shoppingcart.app.controller.CartController;
import com.shoppingcart.app.model.Cart;
import com.shoppingcart.app.service.CartService;
import com.shoppingcart.app.util.AdviceHandlerExceptionResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerBadRequestTest {

    private MockMvc mockMvc;

    @Mock
    CartService cartService;

    @InjectMocks
    CartController cartController;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(cartController)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void add_NewCart_BlankName_ShouldReturnBadRequestStatusCodeAndBadRequestMessage()
            throws Exception{

        String clientJson = "{\n" +
                "    \"id\": 11,\n" +
                "    \"name\": \"\",\n" +
                "    \"createdAt\": \"2017-12-24 09:49:02\",\n" +
                "    \"updatedAt\": \"2017-12-24 09:49:02\"\n" +
                "}";

        when(cartService.add(any(Cart.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/shopping_carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$errorCode", is(400)))
                .andExpect(jsonPath("$message", is("Bad Request")));
    }
}
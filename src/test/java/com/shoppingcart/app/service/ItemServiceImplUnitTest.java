package com.shoppingcart.app.service;


import com.shoppingcart.app.dao.EntityDaoImplTest;
import com.shoppingcart.app.dao.ItemDaoImpl;
import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.ItemNotFoundException;
import com.shoppingcart.app.model.Item;
import org.junit.Before;
import org.junit.Test;
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


public class ItemServiceImplUnitTest extends EntityDaoImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemDaoImpl itemDao;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewItem_BlankDescription_ShouldThrowBadRequestException()
        throws BadRequestException{

        Item item = getItem();
        item.setDescription("");

        when(itemDao.add(item)).thenThrow(DataIntegrityViolationException.class);
        itemService.add(item);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewItem_NullDescription_ShouldThrowBadRequestException()
            throws BadRequestException {
        Item item  = getItem();
        item.setDescription(null);

        when(itemDao.add(item)).thenThrow(DataIntegrityViolationException.class);
        itemService.add(item);
    }
    @Test(expected = BadRequestException.class)
    public void add_NewItem_ZeroCItemId_ShouldThrowBadRequestException()
            throws BadRequestException{

        Item item = getItem();
        item.setId(0);
        when(itemDao.add(item)).thenThrow(DataIntegrityViolationException.class);
        itemService.add(item);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewItem_NegativeItemId_ShouldBadRequestException()
                    throws BadRequestException{
        Item item = getItem();
        item.setId(-1);
        when(itemDao.add(item)).thenThrow(DataIntegrityViolationException.class);
        itemService.add(item);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewItem_DuplicateItemId_ShouldThrowBadRequestException()
                throws  BadRequestException{
        Item item = getItem();
        item.setId(1);

        when(itemDao.add(item)).thenThrow(DuplicateKeyException.class);
        itemService.add(item);
    }



    @Test
    public void add_NewItem_ShouldAddItem() throws BadRequestException{

        Item item  = getItem();

        when(itemDao.add(item)).thenReturn(1);
        itemService.add(item);
    }

    @Test(expected = ItemNotFoundException.class)
    public void deleteById_ItemNotFound_ShouldThrow_ItemNotFoundException()
            throws ItemNotFoundException {

        when(itemDao.deleteById(anyInt())).thenReturn(0);
        int count = itemService.deleteById(1001);

    }

    @Test
    public void deleteById_ItemFound_ShouldReturnCount()
            throws ItemNotFoundException {

        when(itemDao.deleteById(anyInt())).thenReturn(1);
        int count = itemService.deleteById(1001);

        assertEquals(1, count);

    }

    @Test(expected = ItemNotFoundException.class)
    public void update_ItemNotFound_ShouldThrow_ItemNotFoundException()
            throws ItemNotFoundException{

        Item item = getItem();
        when(itemDao.updateItem(item)).thenReturn(0);
        itemService.updateItem(item);
    }

    @Test
    public void update_ItemFound_ShouldReturnUpdatedItem()
            throws ItemNotFoundException{

        Item item = getItem();
        Item expected = getItem();
        expected.setDescription("Updated");

        when(itemDao.updateItem(item)).thenReturn(1);
        when(itemDao.findById(anyInt(),anyInt())).thenReturn(expected);

        Item actual = itemService.updateItem(item);

        assertEquals(actual, expected);
        verify(itemDao).updateItem(item);
        verify(itemDao).findById(anyInt(),anyInt());
    }

    private Item getItem(){

        Item item = new Item();

        item.setId(100);
        item.setDescription("Test_Item");
        item.setUpdatedAt(new Date());
        item.setCreatedAt(new Date());
        item.setShoppingCartId(100);

        return item;
    }
}
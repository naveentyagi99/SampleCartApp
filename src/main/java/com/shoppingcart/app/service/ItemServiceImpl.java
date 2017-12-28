package com.shoppingcart.app.service;

import com.shoppingcart.app.dao.ItemDao;
import com.shoppingcart.app.exception.BadRequestException;
import com.shoppingcart.app.exception.ItemNotFoundException;
import com.shoppingcart.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Override
    public int add(Item item) throws BadRequestException {
        try {
            return itemDao.add(item);
        } catch (Exception e) {
            throw new BadRequestException("Bad Request");
        }
    }

    @Override
    public Item findById(int id, int cart_Id) throws ItemNotFoundException {

        Item item;
        try {
            item = itemDao.findById(id, cart_Id);
            if (item == null) {
                throw new ItemNotFoundException("Item Not Found");
            }
        } catch (Exception e) {
            throw new ItemNotFoundException("Item Not Found");
        }
        return item;
    }

    @Override
    public Item updateItem(Item item) throws ItemNotFoundException {
        int count;
        count = itemDao.updateItem(item);
        if (count < 1) {
            throw new ItemNotFoundException("Item Not Found");
        }
        return findById(item.getId(), item.getShoppingCartId());
    }

    @Override
    public int deleteById(int id) throws ItemNotFoundException {
        int count;
        count = itemDao.deleteById(id);
        if (count < 1) {
            throw new ItemNotFoundException("Item Not Found");
        }
        return count;
    }
}
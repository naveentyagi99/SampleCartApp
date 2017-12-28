package com.shoppingcart.app.dao;

import com.shoppingcart.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.shoppingcart.app.util.Util.getCurrentDate;


@Repository("itemDao")
public class ItemDaoImpl implements ItemDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int add(Item item) {
        return jdbcTemplate.update(
                "INSERT INTO ITEM(id, description, createdAt, updatedAt, shoppingCartId) VALUES(?, ?, ?, ?, ?)",
                item.getId(), item.getDescription(), getCurrentDate(), getCurrentDate(), item.getShoppingCartId());
    }

    @Override
    public Item findById(int item_Id, int cart_Id) {
        return jdbcTemplate.query("SELECT * FROM Item WHERE id=? AND shoppingCartId=?", new Object[]{item_Id, cart_Id},
                new ResultSetExtractor<Item>() {

                    Item item = null;
                    @Override
                    public Item extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        while (resultSet.next()) {
                            item = new Item();

                            item.setId(resultSet.getInt("id"));
                            item.setDescription(resultSet.getString("description"));
                            item.setCreatedAt(resultSet.getTimestamp("createdAt"));
                            item.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                            item.setShoppingCartId(resultSet.getInt("shoppingCartId"));
                        }
                        return item;
                    }
                });
    }

    @Override
    public int deleteById(int id) {
        return  jdbcTemplate.update("DELETE FROM ITEM WHERE id = ?", new Object[]{id});
    }

    @Override
    public int updateItem(Item item) {
        return  jdbcTemplate.update(
                "UPDATE ITEM SET description = ?, updatedAt = ? Where id=?",
                item.getDescription(), getCurrentDate(), item.getId());
    }
}
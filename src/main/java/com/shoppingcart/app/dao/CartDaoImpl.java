package com.shoppingcart.app.dao;

import com.shoppingcart.app.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.shoppingcart.app.util.Util.getCurrentDate;


@Repository("cartDao")
public class CartDaoImpl implements CartDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Cart cart) {
        return jdbcTemplate.update(
                "INSERT INTO CART(id, name, createdAt, updatedAt)VALUES(?,?,?,?)",
                cart.getId(), cart.getName(), getCurrentDate(), getCurrentDate());
    }

    public int deleteById(int id){
        return  jdbcTemplate.update("DELETE FROM CART WHERE id = ?", new Object[]{id});
    }

    public List<Cart> findAll(){
        return jdbcTemplate.query("Select * FROM CART",
                new BeanPropertyRowMapper<Cart>(Cart.class));
    }

    public Cart findById(int cartId){
        return jdbcTemplate.query("SELECT * FROM CART WHERE id=?", new Object[]{cartId},
                new ResultSetExtractor<Cart>() {

                    Cart cart = null;
                    @Override
                    public Cart extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        while (resultSet.next()) {
                            cart = new Cart();

                            cart.setId(resultSet.getInt("id"));
                            cart.setName(resultSet.getString("name"));
                            cart.setCreatedAt(resultSet.getTimestamp("createdAt"));
                            cart.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                        }
                        return cart;
                    }
                });
    }

    public int updateCart(Cart cart){
        return  jdbcTemplate.update(
                "UPDATE CART SET name = ?, updatedAt = ? Where id=?",
                cart.getName(), getCurrentDate(), cart.getId());
    }
}
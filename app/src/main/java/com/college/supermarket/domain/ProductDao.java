package com.college.supermarket.domain;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class ProductDao extends BaseDaoImpl<Product, Integer> {
    public ProductDao(ConnectionSource connectionSource, Class<Product> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Product> findAll() throws SQLException {
        return this.queryForAll();
    }

    public Product findProductById(Integer userId) throws SQLException {
        return this.queryForId(userId);
    }

    public Product findProductByCode(Integer codeProduct) throws SQLException {
        return this.queryForEq("codeProduct", codeProduct).get(0);
    }
}

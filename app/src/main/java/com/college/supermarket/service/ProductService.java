package com.college.supermarket.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.college.supermarket.domain.DbInit;
import com.college.supermarket.domain.Product;
import com.college.supermarket.domain.ProductDao;
import com.college.supermarket.domain.User;
import com.college.supermarket.domain.UserDao;

import java.sql.SQLException;

public class ProductService {
    private static final String TABLE_NAME = "Product";
    private SQLiteDatabase database;
    private DbInit dbInit;
    private ProductDao productDao;

    public ProductService(Context context) throws SQLException {
        dbInit = new DbInit(context);
        database = dbInit.getReadableDatabase();
        productDao = dbInit.getProductDao();
    }

    public void create(Product product) throws Exception {
        productDao.create(product);
    }

    public boolean getProduct(Product product) throws Exception {
        Product productFromDb = productDao.findProductByCode(product.getCodeProduct());
        if (productFromDb != null)
            return true;
        else return false;
    }

    public Double getPriceByCode(Integer code) throws SQLException {
        Product productFromDb = productDao.findProductByCode(code);
        return productFromDb.getPriceProduct();
    }
}

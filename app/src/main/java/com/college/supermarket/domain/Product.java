package com.college.supermarket.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Product")
public class Product {
    @DatabaseField(generatedId = true)
    private int idProduct;

    @DatabaseField
    private String nameProduct;

    @DatabaseField
    private Double priceProduct;

    @DatabaseField
    private Integer codeProduct;

    public int getIdProduct() {
        return idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(Double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Integer getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(Integer codeProduct) {
        this.codeProduct = codeProduct;
    }
}


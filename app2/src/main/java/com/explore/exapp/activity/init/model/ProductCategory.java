package com.explore.exapp.activity.init.model;

import org.litepal.crud.DataSupport;

public class ProductCategory extends DataSupport {

    private int id;

    private int productCategoryId;

    private String productCategoryName;

    private String isLeaf;

    private int upProductCategoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getUpProductCategoryId() {
        return upProductCategoryId;
    }

    public void setUpProductCategoryId(int upProductCategoryId) {
        this.upProductCategoryId = upProductCategoryId;
    }
}

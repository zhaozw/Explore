package com.explore.android.mobile.model;

public class ProductCategory {

	private int productCategoryId;
	private String productCategoryName;
	private String isLeaf;
	private int upProductCategoryId;

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

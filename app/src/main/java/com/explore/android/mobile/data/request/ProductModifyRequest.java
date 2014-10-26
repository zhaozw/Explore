package com.explore.android.mobile.data.request;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.explore.android.mobile.model.ProductKV;

public class ProductModifyRequest implements ExBaseRequest {

	private String userId;
	private String token;
	
	private String productId;
	private String partno;
	private String npartno;
	private String productName;
	private String basePrice;
	private String costPrice;
	private String productModel;
	private String productOtherModel;
	private String productOtherRate;
	private String productUnitStr;
	private String productUnitRateStr;
	private String isB2C;
	private String overDay;
	private String freshDay;
	private String vatRate;
	private String length;
	private String width;
	private String height;
	private String gWeight;
	private String nWeight;
	private String qz;
	private String poUnit;
	private String soUnit;
	private String projectId;
	private String customerDeptId;
	private String relationProductId;
	private String productCategoryId;
	
	private List<ProductKV> productKVList;
//	private String productKeyId;
//	private String productKVId;
//	private String productValue;
	
	private int count;
	public static final int MUST_NUM = 21;
	
	public ProductModifyRequest(){
		count = 1;
	}

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("PRODUCTID", productId);
			json.put("PARTNO", partno);
			json.put("NPARTNO", npartno);
			json.put("PRODUCTNAME", productName);
			json.put("BASEPRICE", basePrice);
			json.put("COSTPRICE", costPrice);
			json.put("PRODUCTMODEL", productModel);
			json.put("PRODUCTOTHERMODEL", productOtherModel);
			json.put("PRODUCTOTHERRATE", productOtherRate);
			json.put("PRODUCTUNITSTR", productUnitStr);
			json.put("PRODUCTUNITRATESTR", productUnitRateStr);
			json.put("ISB2C", isB2C);
			json.put("OVERDAY", overDay);
			json.put("FRESHDAY", freshDay);
			json.put("VATRATE", vatRate);
			json.put("LENGTH", length);
			json.put("WIDTH", width);
			json.put("HEIGHT", height);
			json.put("GWEIGHT", gWeight);
			json.put("NWEIGHT", nWeight);
			json.put("QZ", qz);
			json.put("POUNIT", poUnit);
			json.put("SOUNIT", soUnit);
			json.put("PROJECTID", projectId);
			json.put("CUSTOMERDEPTID", customerDeptId);
			json.put("RELATIONPRODUCTID", relationProductId);
			json.put("PRODUCTCATEGORYID", productCategoryId);
			return json.toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String toJsonString2(){
		JSONArray array = new JSONArray();
		try {
			for (int i = 0; i < productKVList.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("PRODUCTKEYID", productKVList.get(i).getProductKeyId());
				json.put("PRODUCTKVID", productKVList.get(i).getProductKVId());
				json.put("PRODUCTVALUE", productKVList.get(i).getProductValue());
				array.put(json);
			}
			return array.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPartno() {
		return partno;
	}

	public void setPartno(String partno) {
		if(!"".equals(partno)){
			count ++;
		}
		this.partno = partno;
	}

	public String getNpartno() {
		return npartno;
	}

	public void setNpartno(String npartno) {
		if(!"".equals(npartno)){
			count ++;
		}
		this.npartno = npartno;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		if(!"".equals(productName)){
			count ++;
		}
		this.productName = productName;
	}

	public String getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(String basePrice) {
		if(!"".equals(basePrice)){
			count ++;
		}
		this.basePrice = basePrice;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		if(!"".equals(costPrice)){
			count ++;
		}
		this.costPrice = costPrice;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		if(!"".equals(productModel)){
			count ++;
		}
		this.productModel = productModel;
	}

	public String getProductOtherModel() {
		return productOtherModel;
	}

	public void setProductOtherModel(String productOtherModel) {
		if(!"".equals(productOtherModel)){
			count ++;
		}
		this.productOtherModel = productOtherModel;
	}

	public String getProductOtherRate() {
		return productOtherRate;
	}

	public void setProductOtherRate(String productOtherRate) {
		if(!"".equals(productOtherRate)){
			count ++;
		}
		this.productOtherRate = productOtherRate;
	}

	public String getProductUnitStr() {
		return productUnitStr;
	}

	public void setProductUnitStr(String productUnitStr) {
		if(!"".equals(productUnitStr)){
			count ++;
		}
		this.productUnitStr = productUnitStr;
	}

	public String getProductUnitRateStr() {
		return productUnitRateStr;
	}

	public void setProductUnitRateStr(String productUnitRateStr) {
		if(!"".equals(productUnitRateStr)){
			count ++;
		}
		this.productUnitRateStr = productUnitRateStr;
	}

	public String getIsB2C() {
		return isB2C;
	}

	public void setIsB2C(String isB2C) {
		if(!"".equals(isB2C)){
			count ++;
		}
		this.isB2C = isB2C;
	}

	public String getOverDay() {
		return overDay;
	}

	public void setOverDay(String overDay) {
		if(!"".equals(overDay)){
			count ++;
		}
		this.overDay = overDay;
	}

	public String getFreshDay() {
		return freshDay;
	}

	public void setFreshDay(String freshDay) {
		if(!"".equals(freshDay)){
			count ++;
		}
		this.freshDay = freshDay;
	}

	public String getVatRate() {
		return vatRate;
	}

	public void setVatRate(String vatRate) {
		if(!"".equals(vatRate)){
			count ++;
		}
		this.vatRate = vatRate;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		if(!"".equals(length)){
			count ++;
		}
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		if(!"".equals(width)){
			count ++;
		}
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		if(!"".equals(height)){
			count ++;
		}
		this.height = height;
	}

	public String getgWeight() {
		return gWeight;
	}

	public void setgWeight(String gWeight) {
		if(!"".equals(gWeight)){
			count ++;
		}
		this.gWeight = gWeight;
	}

	public String getnWeight() {
		return nWeight;
	}

	public void setnWeight(String nWeight) {
		if(!"".equals(nWeight)){
			count ++;
		}
		this.nWeight = nWeight;
	}

	public String getQz() {
		return qz;
	}

	public void setQz(String qz) {
		if(!"".equals(qz)){
			count ++;
		}
		this.qz = qz;
	}

	public String getPoUnit() {
		return poUnit;
	}

	public void setPoUnit(String poUnit) {
		if(!"".equals(poUnit)){
			count ++;
		}
		this.poUnit = poUnit;
	}

	public String getSoUnit() {
		return soUnit;
	}

	public void setSoUnit(String soUnit) {
		if(!"".equals(soUnit)){
			count ++;
		}
		this.soUnit = soUnit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		if(!"".equals(projectId)){
			count ++;
		}
		this.projectId = projectId;
	}

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		if(!"".equals(customerDeptId)){
			count ++;
		}
		this.customerDeptId = customerDeptId;
	}

	public String getRelationProductId() {
		return relationProductId;
	}

	public void setRelationProductId(String relationProductId) {
		if(!"".equals(relationProductId)){
			count ++;
		}
		this.relationProductId = relationProductId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		if(!"".equals(productCategoryId)){
			count ++;
		}
		this.productCategoryId = productCategoryId;
	}

	public List<ProductKV> getProductKVList() {
		return productKVList;
	}

	public void setProductKVList(List<ProductKV> productKVList) {
		this.productKVList = productKVList;
	}

	public int getCount() {
		return count;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}

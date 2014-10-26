package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutLine1UpdateRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String outLine1Id;
	private String productId;
	private String isDd;
	private String outType;
	private String outQty1;
	private String qtyUnit;
	private String price;
	private String priceUnit;
	private String logNo;
	private String whs;
	private String remarks;

	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("OUTLINE1ID", outLine1Id);
			
			json.put("PRODUCTID", productId);
			
			json.put("ISDD", isDd);
			
			json.put("OUTTYPE", outType);
			
			json.put("OUTQTY1", outQty1);
			
			json.put("QTYUNIT", qtyUnit);
			
			json.put("PRICE", price);
			
			json.put("PRICEUNIT", priceUnit);
			
			json.put("LOGNO", logNo);
			
			json.put("WHS", whs);
			
			json.put("REMARKS", remarks);
			
			return json.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIsDd() {
		return isDd;
	}

	public void setIsDd(String isDd) {
		this.isDd = isDd;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
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

	public String getOutLine1Id() {
		return outLine1Id;
	}

	public void setOutLine1Id(String outLine1Id) {
		this.outLine1Id = outLine1Id;
	}

	public String getOutQty1() {
		return outQty1;
	}

	public void setOutQty1(String outQty1) {
		this.outQty1 = outQty1;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getWhs() {
		return whs;
	}

	public void setWhs(String whs) {
		this.whs = whs;
	}

}

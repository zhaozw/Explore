package com.explore.android.mobile.data.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OutLine1AddRequest implements ExBaseRequest {

	private String outId;
	private String productId;
	private String outType;
	private String partNo;
	private String npartNo;
	private String productName;
	private String whs;
	private String logNo;
	private String outQty1;
	private String qtyUnit;
	private String soPrice;
	private String soPriceLineId;
	private String priceUnit;
	private String remarks;
	private String userId;
	private String token;

	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);

			json.put(TOKEN, token);

			json.put("OUTID", outId);

			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String toJson2String() {

		try {

			JSONArray array = new JSONArray();

			JSONObject json = new JSONObject();

			json.put("SOPRICELINEID", soPriceLineId);

			json.put("PRODUCTID", productId);

			json.put("PARTNO", partNo);

			json.put("NPARTNO", npartNo);

			json.put("PRODUCTNAME", productName);

			json.put("WHS", whs);

			json.put("OUTTYPE", outType);

			json.put("LOGON", logNo);

			json.put("OUTQTY1", outQty1);

			json.put("QTYUNIT", qtyUnit);

			json.put("SOPRICE", soPrice);

			json.put("PRICEUNIT", priceUnit);

			json.put("REMARKS", remarks);

			array.put(json);

			return array.toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String getSoPriceLineId() {
		return soPriceLineId;
	}

	public void setSoPriceLineId(String soPriceLineId) {
		this.soPriceLineId = soPriceLineId;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
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

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getNpartNo() {
		return npartNo;
	}

	public void setNpartNo(String npartNo) {
		this.npartNo = npartNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getWhs() {
		return whs;
	}

	public void setWhs(String whs) {
		this.whs = whs;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
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

	public String getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(String soPrice) {
		this.soPrice = soPrice;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

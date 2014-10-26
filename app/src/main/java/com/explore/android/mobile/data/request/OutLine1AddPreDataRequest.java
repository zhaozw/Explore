package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutLine1AddPreDataRequest implements ExBaseRequest {
	
	private String userId;
	
	private String token;
	
	private String outId;
	
	private String productId;
	
	private String soPriceLogonId;
	
	private String customerId;
	
	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put("USERID", userId);

			json.put("TOKEN", token);

			json.put("OUTID", outId);
			
			json.put("PRODUCTID", productId);
			
			json.put("SOPRICELOGONID", soPriceLogonId);
			
			json.put("CUSTOMERID", customerId);

			return json.toString();

		} catch (JSONException e) {

			return null;

		}
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

	public String getSoPriceLogonId() {
		return soPriceLogonId;
	}

	public void setSoPriceLogonId(String soPriceLogonId) {
		this.soPriceLogonId = soPriceLogonId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}

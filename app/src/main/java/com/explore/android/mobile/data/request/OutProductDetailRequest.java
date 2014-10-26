package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutProductDetailRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String productId;
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();

		try {

			json.put("UID", userId);

			json.put("TOKEN", token);
			
			json.put("PRODUCTID", productId);
			
			return json.toString();

		} catch (JSONException e) {

			e.printStackTrace();

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}

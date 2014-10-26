package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String proId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("USERID", userId);
			
			json.put("TOKEN", token);
			
			json.put("PRODUCTID", proId);
			
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

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
}

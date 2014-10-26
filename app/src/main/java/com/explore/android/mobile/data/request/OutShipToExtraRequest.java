package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutShipToExtraRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String shipToId;
	private String customerDeptId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(ExBaseRequest.USERID, userId);
			
			json.put(ExBaseRequest.TOKEN, token);
			
			json.put("SHIPTO", shipToId);
			
			json.put("CUSTOMERDEPTID", customerDeptId);
			
			return json.toString();
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
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

	public String getShipToId() {
		return shipToId;
	}

	public void setShipToId(String shipToId) {
		this.shipToId = shipToId;
	}
	
}

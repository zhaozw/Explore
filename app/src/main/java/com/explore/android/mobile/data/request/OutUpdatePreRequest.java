package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutUpdatePreRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String customerDeptId;
	private String shipTo;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("SHIPTO", shipTo);
			
			json.put("CUSTOMERDEPTID", customerDeptId);
			
			return json.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
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

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

}

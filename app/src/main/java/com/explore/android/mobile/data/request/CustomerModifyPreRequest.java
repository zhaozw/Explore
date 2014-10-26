package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerModifyPreRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String customerId;
	private String type;

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);

			json.put(TOKEN, token);

			json.put("CUSTOMERID", customerId);
			
			json.put("TYPE", type);

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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

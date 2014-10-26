package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutDetailRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String outId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("USERID", userId);
			
			json.put("TOKEN", token);
			
			json.put("OUTID", outId);
			
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
	
}

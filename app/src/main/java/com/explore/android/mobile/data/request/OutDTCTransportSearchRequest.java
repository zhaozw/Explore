package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutDTCTransportSearchRequest implements ExBaseRequest{

	private String userId;
	
	private String token;
	
	private String transportCode1;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("TRANSPORTCODE1", transportCode1);
			
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

	public String getTransportCode1() {
		return transportCode1;
	}

	public void setTransportCode1(String transportCode1) {
		this.transportCode1 = transportCode1;
	}

}

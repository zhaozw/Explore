package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutLine1DeleteRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String outLine1Id;

	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("OUTLINE1ID", outLine1Id);
			
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

	public String getOutLine1Id() {
		return outLine1Id;
	}

	public void setOutLine1Id(String outLine1Id) {
		this.outLine1Id = outLine1Id;
	}

}

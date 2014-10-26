package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutProjectExtraRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String projectId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(ExBaseRequest.USERID, userId);
			
			json.put(ExBaseRequest.TOKEN, token);
			
			json.put("PROJECTID", projectId);
			
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}

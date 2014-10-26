package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutCusDeptExtraRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String deptId;
	private String type;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(ExBaseRequest.USERID, userId);
			
			json.put(ExBaseRequest.TOKEN, token);
			
			json.put("CUSTOMERDEPTID", deptId);
			
			json.put("TYPE", type);
			
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

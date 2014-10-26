package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class AuditEmpRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String myself;
	private String type;
	private String depeId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);

			json.put(TOKEN, token);
			
			json.put("MYSELF", myself);
			
			json.put("TYPE", type);
			
			json.put("DEPTID", depeId);

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

	public String getMyself() {
		return myself;
	}

	public void setMyself(String myself) {
		this.myself = myself;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepeId() {
		return depeId;
	}

	public void setDepeId(String depeId) {
		this.depeId = depeId;
	}


}

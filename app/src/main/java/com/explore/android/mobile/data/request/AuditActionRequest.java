package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class AuditActionRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String auditId;
	private String empId;
	private String type;
	private String content;
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);

			json.put(TOKEN, token);
			
			json.put("TYPE", type);
			
			json.put("EMPLOYEEID", empId);
			
			json.put("AUDITID", auditId);
			
			json.put("AUDITCONTENT", content);
			
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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

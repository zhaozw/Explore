package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerAddPreRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String companyCode;

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put("USERID", userId);

			json.put("TOKEN", token);

			json.put("COMPANYCODE", companyCode);

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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}

package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class TransportLine2Request implements ExBaseRequest {

	private String userId;
	private String token;
	private String transportId;
	private String loc;

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put("USERID", userId);
			json.put("TOKEN", token);
			json.put("TRANSPORTID", transportId);
			json.put("LOC", loc);
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

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

}

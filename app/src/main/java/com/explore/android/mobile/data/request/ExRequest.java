package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class ExRequest implements ExBaseRequest{

	private String token;
	private String imei;
	private String imsi;
	private String userId;

	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);

			json.put(TOKEN, token);
			
			if(!"".equals(imsi) && imsi != null){
				json.put("IMSI", imsi);
			}
			
			if(!"".equals(imei) && imei != null){
				json.put("IMEI", imei);
			}
			
			return json.toString();

		} catch (JSONException e) {

			e.printStackTrace();

			return null;

		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

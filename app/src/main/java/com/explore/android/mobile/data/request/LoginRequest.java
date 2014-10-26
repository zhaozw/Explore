package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequest implements ExBaseRequest{

	private String userName;

	private String password;

	private String imei;

	private String imsi;

	public String toJsonString() {
		
		JSONObject json = new JSONObject();

		try {

			json.put("USERNAME", userName);

			json.put("PASSWORD", password);

			json.put("IMEI", imei);

			json.put("IMSI", imsi);

			return json.toString();

		} catch (JSONException e) {

			e.printStackTrace();

			return null;

		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}

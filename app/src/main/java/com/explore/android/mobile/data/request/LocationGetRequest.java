package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationGetRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String type;
	private String address;
	private String lat;	// 纬度
	private String lng; // 经度
	
	private static final String TYPE_GPS = "GPS"; // 根据地址取经纬度信息
	private static final String TYPE_ADDRESS = "ADDRESS"; // 根据经纬度取地址信息
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {

			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("TYPE", type);
			if(TYPE_GPS.equals(type)){
				json.put("ADDRESS", address);
			} else if(TYPE_ADDRESS.equals(type)){
				json.put("LAT", lat);
				json.put("LNG", lng);
			}
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

	public String getType() {
		return type;
	}

	public void setTypeGPS() {
		this.type = TYPE_GPS;
	}
	
	public void setTypeAddress() {
		this.type = TYPE_ADDRESS;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
}

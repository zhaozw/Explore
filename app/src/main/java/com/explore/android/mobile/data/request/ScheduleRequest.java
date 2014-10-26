package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleRequest implements ExBaseRequest{
	
	private String token;
	private String userId;
	private String year;
	private String month;
	private String date;
	
	public String toJsonString(){
		
		JSONObject json = new JSONObject();

		try {

			json.put("TOKEN", token);

			json.put("USERID", userId);

			json.put("Y", year);

			json.put("M", month);
			
			json.put("D", date);

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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}

package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutProductSearchRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String keyWord;
	private String outId;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("USERID", userId);
			
			json.put("TOKEN", token);
			
			json.put("KEYWORD", keyWord);
			
			json.put("OUTID", outId);
			
			return json.toString();
			
		} catch (JSONException e) {
			
			return null;
			
		}
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}

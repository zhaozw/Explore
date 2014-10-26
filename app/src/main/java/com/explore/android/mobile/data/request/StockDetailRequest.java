package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class StockDetailRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String stockLineId;

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {
			
			json.put("USERID", userId);
			
			json.put("TOKEN", token);
			
			json.put("STOCKLINEID", stockLineId);

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

	public String getStockLineId() {
		return stockLineId;
	}

	public void setStockLineId(String stockLineId) {
		this.stockLineId = stockLineId;
	}

}

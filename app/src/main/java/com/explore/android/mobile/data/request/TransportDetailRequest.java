package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class TransportDetailRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String transportId;
	private int psize;
	private int pageNum;

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put("USERID", userId);
			json.put("TOKEN", token);
			json.put("TRANSPORTID", transportId);
			if (psize >0) {
				json.put("PAGESIZE", "" + psize);
			}
			if (pageNum > 0) {
				json.put("PAGENUM", "" + pageNum);
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

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

	public int getPsize() {
		return psize;
	}

	public void setPsize(int psize) {
		this.psize = psize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}

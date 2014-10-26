package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DataLoaderRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String keyword;
	private String type;
	private int psize;
	private int pageNum;
	private String[] extraParams;
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();

		try {

			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("KEYWORD", keyword);

			json.put("PAGESIZE", "" + psize);
			
			json.put("PAGENUM", "" + pageNum);
			
			json.put("TYPE", type);
			
			if(extraParams != null){
				int length = extraParams.length;
				if (length > 1 && length%2 == 0 ) {
					for (int i = 0; i < length; i=i+2) {
						json.put(extraParams[i], extraParams[i+1]);
						Log.e(null, extraParams[i]+","+extraParams[i+1]);
					}
				}
			}
			
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

	public String[] getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String[] extraParams) {
		this.extraParams = extraParams;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

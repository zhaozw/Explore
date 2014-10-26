package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerSearchRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String customerNameCN; 	// 客户名称
	private String customerCode; 	// 客户编号
	private int pSize;
	private int pageNum;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put("USERID", userId);
			
			json.put("TOKEN",token);
			
			json.put("CUSTOMERNAMECN", customerNameCN);
			
			json.put("CUSTOMERCODE", customerCode);
			
			json.put("PAGESIZE", ""+pSize);
			
			json.put("PAGENUM", ""+pageNum);
			
			return json.toString();
			
		} catch (JSONException e) {
			
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

	public String getCustomerNameCN() {
		return customerNameCN;
	}

	public void setCustomerNameCN(String customerNameCN) {
		this.customerNameCN = customerNameCN;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}

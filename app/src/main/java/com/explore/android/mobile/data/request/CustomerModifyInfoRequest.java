package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerModifyInfoRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String customerId;
	private String type;
	private String customerNameCN;
	private String customerNameCNs;
	private String customerNameEN;
	private String customerNameENs;
	private String companyCode;
	private String customerCode;
	private String customerType;
	private String mCustomerType;
	private String isLock;
	private String isModify;
	

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();
		try {
			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("CUSTOMERID", customerId);
			json.put("TYPE", type);
			json.put("CUSTOMERNAMECN", customerNameCN);
			json.put("CUSTOMERNAMECNS", customerNameCNs);
			json.put("CUSTOMERNAMEEN", customerNameEN);
			json.put("CUSTOMERNAMEENS", customerNameENs);
			json.put("COMPANYCODE", companyCode);
			json.put("CUSTOMERCODE", customerCode);
			json.put("CUSTOMERTYPE", customerType);
			json.put("MCUSTOMERTYPE", mCustomerType);
			json.put("ISLOCK", isLock);
			json.put("ISMODIFY", isModify);

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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerNameCN() {
		return customerNameCN;
	}

	public void setCustomerNameCN(String customerNameCN) {
		this.customerNameCN = customerNameCN;
	}

	public String getCustomerNameCNs() {
		return customerNameCNs;
	}

	public void setCustomerNameCNs(String customerNameCNs) {
		this.customerNameCNs = customerNameCNs;
	}

	public String getCustomerNameEN() {
		return customerNameEN;
	}

	public void setCustomerNameEN(String customerNameEN) {
		this.customerNameEN = customerNameEN;
	}

	public String getCustomerNameENs() {
		return customerNameENs;
	}

	public void setCustomerNameENs(String customerNameENs) {
		this.customerNameENs = customerNameENs;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getmCustomerType() {
		return mCustomerType;
	}

	public void setmCustomerType(String mCustomerType) {
		this.mCustomerType = mCustomerType;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

}

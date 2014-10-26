package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutTTCCreateRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String outType;
	private String outType2;
	private String outCode2;
	private String customerDept;
	private String warehouse;
	private String soPriceLogon;
	private String logon;
	private String shipTo;
	private String shipFrom;
	private String vatTo;
	private String shipToAdress;
	private String outTime;
	private String remarks;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(ExBaseRequest.USERID, userId);
			
			json.put(ExBaseRequest.TOKEN, token);
			
			json.put("OUTTYPE", outType);
			
			json.put("OUTTYPE2", outType2);
			
			json.put("OUTCODE2", outCode2);
			
			json.put("CUSTOMERDEPTID", customerDept);
			
			json.put("WAREHOUSEID", warehouse);
			
			json.put("SOPRICELOGON", soPriceLogon);
			
			json.put("LOGON", logon);
			
			json.put("SHIPTO", shipTo);
			
			json.put("SHIPFROM", shipFrom);
			
			json.put("VATTO", vatTo);
			
			json.put("SHIPTOADRESS", shipToAdress);
			
			json.put("OUTTIME", outTime);
			
			json.put("REMARKS", remarks);
			
			return json.toString();
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}

	public String getShipFrom() {
		return shipFrom;
	}

	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}

	public String getUserId() {
		return userId;
	}

	public String getOutCode2() {
		return outCode2;
	}

	public void setOutCode2(String outCode2) {
		this.outCode2 = outCode2;
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

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getCustomerDept() {
		return customerDept;
	}

	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getSoPriceLogon() {
		return soPriceLogon;
	}

	public void setSoPriceLogon(String soPriceLogon) {
		this.soPriceLogon = soPriceLogon;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getVatTo() {
		return vatTo;
	}

	public void setVatTo(String vatTo) {
		this.vatTo = vatTo;
	}

	public String getShipToAdress() {
		return shipToAdress;
	}

	public void setShipToAdress(String shipToAdress) {
		this.shipToAdress = shipToAdress;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOutType2() {
		return outType2;
	}

	public void setOutType2(String outType2) {
		this.outType2 = outType2;
	}

}

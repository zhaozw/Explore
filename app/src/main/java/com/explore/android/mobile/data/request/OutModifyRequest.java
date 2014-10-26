package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutModifyRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String outId;
	private String outType;
	private String outType2;
	private String outCode1;
	private String outCode2;
	private String shipToAdress;
	private String remarks;
	private String outTime;
	private String customerDeptId;
	private String shipTo;
	private String wareHouseId;
	private String projectId;
	private String vendorId;
	private String vatTo;
	private String soPriceLogonId;
	private String isDd;
	
	@Override
	public String toJsonString() {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put(USERID, userId);
			
			json.put(TOKEN, token);
			
			json.put("WAREHOUSEID", wareHouseId);
			
			json.put("PROJECTID", projectId);
			
			json.put("VENDORID", vendorId);
		
			json.put("VATTO", vatTo);
			
			json.put("SOPRICELOGONID", soPriceLogonId);
			
			json.put("OUTID", outId);
			
			json.put("OUTTYPE", outType);
			
			json.put("OUTTYPE2", outType2);
			
			json.put("OUTCODE1", outCode1);
			
			json.put("OUTCODE2", outCode2);
			
			json.put("CUSTOMERDEPTID", customerDeptId);
			
			json.put("SHIPTOADRESS", shipToAdress);
			
			json.put("REMARKS", remarks);
			
			json.put("OUTTIME", outTime);
			
			json.put("SHIPTO", shipTo);
			
			return json.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVatTo() {
		return vatTo;
	}

	public void setVatTo(String vatTo) {
		this.vatTo = vatTo;
	}

	public String getSoPriceLogonId() {
		return soPriceLogonId;
	}

	public void setSoPriceLogonId(String soPriceLogonId) {
		this.soPriceLogonId = soPriceLogonId;
	}

	public String getIsDd() {
		return isDd;
	}

	public void setIsDd(String isDd) {
		this.isDd = isDd;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getOutType2() {
		return outType2;
	}

	public void setOutType2(String outType2) {
		this.outType2 = outType2;
	}

	public String getOutCode1() {
		return outCode1;
	}

	public void setOutCode1(String outCode1) {
		this.outCode1 = outCode1;
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

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getOutCode2() {
		return outCode2;
	}

	public void setOutCode2(String outCode2) {
		this.outCode2 = outCode2;
	}

	public String getShipToAdress() {
		return shipToAdress;
	}

	public void setShipToAdress(String shipToAdress) {
		this.shipToAdress = shipToAdress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	
}

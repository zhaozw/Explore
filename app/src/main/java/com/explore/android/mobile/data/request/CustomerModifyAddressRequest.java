package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerModifyAddressRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String customerId;
	private String customerDeptId;
	private String type;

	private String addressId;

	// 地址信息
	private String addressProvince;
	private String addressCity;
	private String addressArea;
	private String addressType;
	private String addressAddress;
	private String addressPostCode;
	private String addressContactName;
	private String addressMtel;
	private String addressTel;
	private String addressFax;
	private String addressEmail;
	private String addressRemarks; // #
	private String addressLng;
	private String addressLat;
	private String addressIsDefault;

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("CUSTOMERID", customerId);
			json.put("TYPE", type);
			json.put("ADDRESSID", addressId);
			json.put("ADDRESSPROVINCE", addressProvince);
			json.put("ADDRESSCITY", addressCity);
			json.put("ADDRESSAREA", addressArea);
			json.put("ADDRESSTYPE", addressType);
			json.put("ADDRESSADDRESS", addressAddress);
			json.put("ADDRESSPOSTCODE", addressPostCode);
			json.put("ADDRESSCONTACTNAME", addressContactName);
			json.put("ADDRESSMTEL", addressMtel);
			json.put("ADDRESSTEL", addressTel);
			json.put("ADDRESSFAX", addressFax);
			json.put("ADDRESSEMAIL", addressEmail);
			json.put("ADDRESSREMARKS", addressRemarks);
			json.put("ADDRESSING", addressLng);
			json.put("ADDRESSLAT", addressLat);
			json.put("ADDRESSISDEFAULT", addressIsDefault);
			json.put("CUSTOMERDEPTID", customerDeptId);

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

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressArea() {
		return addressArea;
	}

	public void setAddressArea(String addressArea) {
		this.addressArea = addressArea;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressAddress() {
		return addressAddress;
	}

	public void setAddressAddress(String addressAddress) {
		this.addressAddress = addressAddress;
	}

	public String getAddressPostCode() {
		return addressPostCode;
	}

	public void setAddressPostCode(String addressPostCode) {
		this.addressPostCode = addressPostCode;
	}

	public String getAddressContactName() {
		return addressContactName;
	}

	public void setAddressContactName(String addressContactName) {
		this.addressContactName = addressContactName;
	}

	public String getAddressMtel() {
		return addressMtel;
	}

	public void setAddressMtel(String addressMtel) {
		this.addressMtel = addressMtel;
	}

	public String getAddressTel() {
		return addressTel;
	}

	public void setAddressTel(String addressTel) {
		this.addressTel = addressTel;
	}

	public String getAddressFax() {
		return addressFax;
	}

	public void setAddressFax(String addressFax) {
		this.addressFax = addressFax;
	}

	public String getAddressEmail() {
		return addressEmail;
	}

	public void setAddressEmail(String addressEmail) {
		this.addressEmail = addressEmail;
	}

	public String getAddressRemarks() {
		return addressRemarks;
	}

	public void setAddressRemarks(String addressRemarks) {
		this.addressRemarks = addressRemarks;
	}

	public String getAddressLng() {
		return addressLng;
	}

	public void setAddressLng(String addressLng) {
		this.addressLng = addressLng;
	}

	public String getAddressLat() {
		return addressLat;
	}

	public void setAddressLat(String addressLat) {
		this.addressLat = addressLat;
	}

	public String getAddressIsDefault() {
		return addressIsDefault;
	}

	public void setAddressIsDefault(String addressIsDefault) {
		this.addressIsDefault = addressIsDefault;
	}

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
	}

}

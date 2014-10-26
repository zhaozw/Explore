package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 带注释'#'的为非必填项
 * @author Ryan
 */
public class CustomerAddRequest implements ExBaseRequest {

	private String userId;
	private String token;
	
	//商品信息
	private String customerNameCN;
	private String customerNameCNs;
	private String customerNameEN; 		// #
	private String customerNameEns; 	// #
	private String companyCode; 		// #
	
	//地址信息
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
	private String addressRemarks; 		// #
	private String addressLng;
	private String addressLat;
	private String addressIsDefault;
	
	//经营单位信息
	private String cusCusDeptId;
	private String cusAmountMoney;
	private String cusKaType;			 // #
	private String cusArea;			 	 // #
	private String cusDzType;
	private String cusZqType;
	private String cusGdDay;
	private String cusZqDay;
	private String cusZqMonth;
	private String cusIsVat;
	private String cusVatTo;
	private String cusIsDh;
	private String cusDhRate;
	
	private int count = 0;
	public static final int MAX_COUNT_GDR_DH = 25;
	public static final int MAX_COUNT_GDR_NODH = 24;
	public static final int MAX_COUNT_GDT_DH = 24;
	public static final int MAX_COUNT_GDT_NODH = 23;
	// address:14
	// info:2
	// dept:6(3,2,2,1)
	// dept->固定日 && isDh->Y：9+16
	// dept->固定日 && isDh->N：8+16
	// dept->固定天 && isDh->Y：8+16
	// dept->固定天 && isDh->N：7+16
	

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("CUSTOMERNAMECN", customerNameCN);
			json.put("CUSTOMERNAMECNS", customerNameCNs);
			json.put("CUSTOMERNAMEEN", customerNameEN);
			json.put("CUSTOMERNAMEENS", customerNameEns);
			json.put("COMPANYCODE", companyCode);
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
			json.put("CUSCUSDEPTID", cusCusDeptId);
			json.put("CUSAMOUNTMONEY", cusAmountMoney);
			json.put("CUSKATYPE", cusKaType);
			json.put("CUSAREA", cusArea);
			json.put("CUSDZTYPE", cusDzType);
			json.put("CUSZQTYPE", cusZqType);
			json.put("CUSGDDAY", cusGdDay);
			json.put("CUSZQDAY", cusZqDay);
			json.put("CUSZQMONTH", cusZqMonth);
			json.put("CUSISVAT", cusIsVat);
			json.put("CUSVATTO", cusVatTo);
			json.put("CUSISDH", cusIsDh);
			json.put("CUSDHRATE", cusDhRate);
			
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCustomerNameCN(String customerNameCN) {
		if(!"".equals(customerNameCN)){
			count++;
		}
		this.customerNameCN = customerNameCN;
	}

	public void setCustomerNameCNs(String customerNameCNs) {
		if(!"".equals(customerNameCNs)){
			count++;
		}
		this.customerNameCNs = customerNameCNs;
	}

	public void setCustomerNameEN(String customerNameEN) {
		this.customerNameEN = customerNameEN;
	}

	public void setCustomerNameEns(String customerNameEns) {
		this.customerNameEns = customerNameEns;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setAddressProvince(String addressProvince) {
		if(!"".equals(addressProvince)){
			count++;
		}
		this.addressProvince = addressProvince;
	}

	public void setAddressCity(String addressCity) {
		if(!"".equals(addressCity)){
			count++;
		}
		this.addressCity = addressCity;
	}

	public void setAddressArea(String addressArea) {
		if(!"".equals(addressArea)){
			count++;
		}
		this.addressArea = addressArea;
	}

	public void setAddressType(String addressType) {
		if(!"".equals(addressType)){
			count++;
		}
		this.addressType = addressType;
	}

	public void setAddressAddress(String addressAddress) {
		if(!"".equals(addressAddress)){
			count++;
		}
		this.addressAddress = addressAddress;
	}

	public void setAddressPostCode(String addressPostCode) {
		if(!"".equals(addressPostCode)){
			count++;
		}
		this.addressPostCode = addressPostCode;
	}

	public void setAddressContactName(String addressContactName) {
		if(!"".equals(addressContactName)){
			count++;
		}
		this.addressContactName = addressContactName;
	}

	public void setAddressMtel(String addressMtel) {
		if(!"".equals(addressMtel)){
			count++;
		}
		this.addressMtel = addressMtel;
	}

	public void setAddressTel(String addressTel) {
		if(!"".equals(addressTel)){
			count++;
		}
		this.addressTel = addressTel;
	}

	public void setAddressFax(String addressFax) {
		if(!"".equals(addressFax)){
			count++;
		}
		this.addressFax = addressFax;
	}

	public void setAddressEmail(String addressEmail) {
		if(!"".equals(addressEmail)){
			count++;
		}
		this.addressEmail = addressEmail;
	}

	public void setAddressRemarks(String addressRemarks) {
		this.addressRemarks = addressRemarks;
	}

	public void setAddressLng(String addressLng) {
		if(!"".equals(addressLng)){
			count++;
		}
		this.addressLng = addressLng;
	}

	public void setAddressLat(String addressLat) {
		if(!"".equals(addressLat)){
			count++;
		}
		this.addressLat = addressLat;
	}

	public void setAddressIsDefault(String addressIsDefault) {
		if(!"".equals(addressIsDefault)){
			count++;
		}
		this.addressIsDefault = addressIsDefault;
	}

	public void setCusCusDeptId(String cusCusDeptId) {
		if(!"".equals(cusCusDeptId)){
			count++;
		}
		this.cusCusDeptId = cusCusDeptId;
	}

	public void setCusAmountMoney(String cusAmountMoney) {
		if(!"".equals(cusAmountMoney)){
			count++;
		}
		this.cusAmountMoney = cusAmountMoney;
	}

	public void setCusKaType(String cusKaType) {
		this.cusKaType = cusKaType;
	}

	public void setCusDzType(String cusDzType) {
		if(!"".equals(cusDzType)){
			count++;
		}
		this.cusDzType = cusDzType;
	}

	public void setCusZqType(String cusZqType) {
		if(!"".equals(cusZqType)){
			count++;
		}
		this.cusZqType = cusZqType;
	}

	public void setCusGdDay(String cusGdDay) {
		if(!"".equals(cusGdDay)){
			count++;
		}
		this.cusGdDay = cusGdDay;
	}

	public void setCusZqDay(String cusZqDay) {
		if(!"".equals(cusZqDay)){
			count++;
		}
		this.cusZqDay = cusZqDay;
	}

	public void setCusZqMonth(String cusZqMonth) {
		if(!"".equals(cusZqMonth)){
			count++;
		}
		this.cusZqMonth = cusZqMonth;
	}

	public void setCusIsVat(String cusIsVat) {
		if(!"".equals(cusIsVat)){
			count++;
		}
		this.cusIsVat = cusIsVat;
	}

	public void setCusIsDh(String cusIsDh) {
		if(!"".equals(cusIsDh)){
			count++;
		}
		this.cusIsDh = cusIsDh;
	}
	
	public void setCusDhRate(String cusDhRate) {
		if(!"".equals(cusDhRate)){
			count++;
		}
		this.cusDhRate = cusDhRate;
	}
	
	public void setCusVatTo(String cusVatTo) {
		this.cusVatTo = cusVatTo;
	}

	public void setCusArea(String cusArea) {
		this.cusArea = cusArea;
	}
	
	//-------------- gets(*)-------------

	public String getCusArea() {
		return cusArea;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public String getCustomerNameCN() {
		return customerNameCN;
	}

	public String getCustomerNameCNs() {
		return customerNameCNs;
	}

	public String getCustomerNameEN() {
		return customerNameEN;
	}

	public String getCustomerNameEns() {
		return customerNameEns;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public String getAddressArea() {
		return addressArea;
	}

	public String getAddressType() {
		return addressType;
	}

	public String getAddressAddress() {
		return addressAddress;
	}

	public String getAddressPostCode() {
		return addressPostCode;
	}

	public String getAddressContactName() {
		return addressContactName;
	}

	public String getAddressMtel() {
		return addressMtel;
	}

	public String getAddressTel() {
		return addressTel;
	}

	public String getAddressFax() {
		return addressFax;
	}

	public String getAddressEmail() {
		return addressEmail;
	}

	public String getAddressRemarks() {
		return addressRemarks;
	}

	public String getAddressLng() {
		return addressLng;
	}

	public String getAddressLat() {
		return addressLat;
	}

	public String getCusCusDeptId() {
		return cusCusDeptId;
	}

	public String getCusAmountMoney() {
		return cusAmountMoney;
	}

	public String getCusKaType() {
		return cusKaType;
	}

	public String getCusDzType() {
		return cusDzType;
	}

	public String getCusZqType() {
		return cusZqType;
	}

	public String getCusGdDay() {
		return cusGdDay;
	}

	public String getCusZqDay() {
		return cusZqDay;
	}

	public String getCusZqMonth() {
		return cusZqMonth;
	}

	public String getCusIsVat() {
		return cusIsVat;
	}

	public String getCusIsDh() {
		return cusIsDh;
	}

	public String getCusDhRate() {
		return cusDhRate;
	}

	public String getAddressIsDefault() {
		return addressIsDefault;
	}

	public String getCusVatTo() {
		return cusVatTo;
	}

}

package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerModifyDeptRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String customerId;
	private String type;
	
	// 经营单位信息
	private String cvdId;
	private String cusCusDeptId;
	private String cusAmountMoney;
	private String cusKaType; // #
	private String cusArea; // #
	private String cusDzType;
	private String cusZqType;
	private String cusGdDay;
	private String cusZqDay;
	private String cusZqMonth;
	private String cusIsVat;
	private String cusVatTo;
	private String cusIsDh;
	private String cusDhRate;
	
	private String payMoney;
	private String receiveMoney;

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();
		try {
			json.put(USERID, userId);
			json.put(TOKEN, token);
			json.put("CUSTOMERID", customerId);
			json.put("TYPE", type);
			json.put("CVDID", cvdId);
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
			json.put("CUSISDH", cusIsDh);
			json.put("CUSDHRATE", cusDhRate);
			json.put("PAYMONEY", payMoney);
			json.put("RECEIVEMONEY", receiveMoney);
			json.put("CUSVATTO", cusVatTo);

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

	public String getCvdId() {
		return cvdId;
	}

	public void setCvdId(String cvdId) {
		this.cvdId = cvdId;
	}

	public String getCusCusDeptId() {
		return cusCusDeptId;
	}

	public void setCusCusDeptId(String cusCusDeptId) {
		this.cusCusDeptId = cusCusDeptId;
	}

	public String getCusAmountMoney() {
		return cusAmountMoney;
	}

	public void setCusAmountMoney(String cusAmountMoney) {
		this.cusAmountMoney = cusAmountMoney;
	}

	public String getCusKaType() {
		return cusKaType;
	}

	public void setCusKaType(String cusKaType) {
		this.cusKaType = cusKaType;
	}

	public String getCusArea() {
		return cusArea;
	}

	public void setCusArea(String cusArea) {
		this.cusArea = cusArea;
	}

	public String getCusDzType() {
		return cusDzType;
	}

	public void setCusDzType(String cusDzType) {
		this.cusDzType = cusDzType;
	}

	public String getCusZqType() {
		return cusZqType;
	}

	public void setCusZqType(String cusZqType) {
		this.cusZqType = cusZqType;
	}

	public String getCusGdDay() {
		return cusGdDay;
	}

	public void setCusGdDay(String cusGdDay) {
		this.cusGdDay = cusGdDay;
	}

	public String getCusZqDay() {
		return cusZqDay;
	}

	public void setCusZqDay(String cusZqDay) {
		this.cusZqDay = cusZqDay;
	}

	public String getCusZqMonth() {
		return cusZqMonth;
	}

	public void setCusZqMonth(String cusZqMonth) {
		this.cusZqMonth = cusZqMonth;
	}

	public String getCusIsVat() {
		return cusIsVat;
	}

	public void setCusIsVat(String cusIsVat) {
		this.cusIsVat = cusIsVat;
	}

	public String getCusIsDh() {
		return cusIsDh;
	}

	public void setCusIsDh(String cusIsDh) {
		this.cusIsDh = cusIsDh;
	}

	public String getCusDhRate() {
		return cusDhRate;
	}

	public void setCusDhRate(String cusDhRate) {
		this.cusDhRate = cusDhRate;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(String receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public String getCusVatTo() {
		return cusVatTo;
	}

	public void setCusVatTo(String cusVatTo) {
		this.cusVatTo = cusVatTo;
	}

}

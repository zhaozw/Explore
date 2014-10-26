package com.explore.android.mobile.data.request;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.explore.android.mobile.model.OutDTCTransportData;

public class OutDTCCreateRequest implements ExBaseRequest {

	private String userId;

	private String token;
	
	private String transportCode1;

	private String transportId;
	
	private String outType;

	private String customerDept;

	private String soPriceLogon;

	private String shipTo;

	private String vatTo;

	private String shipToAdress;

	private String outTime;

	private List<OutDTCTransportData> results;

	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();

		try {

			json.put(ExBaseRequest.USERID, userId);

			json.put(ExBaseRequest.TOKEN, token);

			json.put("OUTTYPE", outType);
			
			json.put("TRANSPORTCODE1", transportCode1);

			json.put("CUSTOMERDEPTID", customerDept);

			json.put("SOPRICELOGON", soPriceLogon);
			
			json.put("TRANSPORTID", transportId);

			json.put("SHIPTO", shipTo);

			json.put("VATTO", vatTo);

			json.put("SHIPTOADRESS", shipToAdress);

			json.put("OUTTIME", outTime);

			return json.toString();

		} catch (JSONException e) {

			e.printStackTrace();

		}

		return null;
	}

	public String toJson2String() {

		JSONArray jsonArray = new JSONArray();
		
		try {
			
			for (int i = 0; i < results.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				
				OutDTCTransportData data = results.get(i);
				jsonObject.put("transportLine1Id", data.getTransportLine1Id());
				jsonObject.put("partno", data.getPartno());
				jsonObject.put("npartNo", data.getNpartno());
				jsonObject.put("productName", data.getProductName());
				jsonObject.put("transportQty1S", data.getTransportQty1S());
				jsonObject.put("transportQty1B", data.getTransportQty1B());
				jsonObject.put("outQty", data.getOutQty());
				jsonObject.put("soPrice", data.getSoPrice());
				jsonArray.put(jsonObject);
			}
			
			return jsonArray.toString();
					
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

	public String getSoPriceLogon() {
		return soPriceLogon;
	}

	public void setSoPriceLogon(String soPriceLogon) {
		this.soPriceLogon = soPriceLogon;
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

	public String getTransportCode1() {
		return transportCode1;
	}

	public void setTransportCode1(String transportCode1) {
		this.transportCode1 = transportCode1;
	}

	public List<OutDTCTransportData> getResults() {
		return results;
	}

	public void setResults(List<OutDTCTransportData> results) {
		this.results = results;
	}

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

}

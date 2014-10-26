package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class OutSearchRequest implements ExBaseRequest{
	
	private String userId;
	private String token;
	private String customerDept;
	private String warehouse;
	private String shipTo;
	private String vatTo;
	private String outCode1;
	private String outCode2;
	private String outTimeBegin;
	private String outTimeEnd;
	private String outType;
	private String sts;
	private int psize;
	private int pageNum;
	// 2013-12-25 新增字段
	private String shipFrom;
	private String remarks;
	
	
	@Override
	public String toJsonString() {

		JSONObject json = new JSONObject();
		
		try {
			
			json.put(ExBaseRequest.USERID, userId);
			json.put(ExBaseRequest.TOKEN, token);
			json.put("CUSTOMERDEPTID", customerDept);
			json.put("WAREHOUSEID", warehouse);
			json.put("SHIPTO", shipTo);
			json.put("VATTO", vatTo);
			json.put("OUTCODE1", outCode1);
			json.put("OUTCODE2", outCode2);
			json.put("OUTTIMEBEGIN", outTimeBegin);
			json.put("OUTTIMEEND", outTimeEnd);
			json.put("OUTTYPE", outType);
			json.put("STS", sts);
			json.put("PAGESIZE", ""+psize);
			json.put("PAGENUM", ""+pageNum);
			
			json.put("SHIPFROM", shipFrom);
			json.put("REMARKS", remarks);
			
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

	public String getOutCode1() {
		return outCode1;
	}

	public void setOutCode1(String outCode1) {
		this.outCode1 = outCode1;
	}

	public String getOutCode2() {
		return outCode2;
	}

	public void setOutCode2(String outCode2) {
		this.outCode2 = outCode2;
	}

	public String getOutTimeBegin() {
		return outTimeBegin;
	}

	public void setOutTimeBegin(String outTimeBegin) {
		this.outTimeBegin = outTimeBegin;
	}

	public String getOutTimeEnd() {
		return outTimeEnd;
	}

	public void setOutTimeEnd(String outTimeEnd) {
		this.outTimeEnd = outTimeEnd;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
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

	public String getShipFrom() {
		return shipFrom;
	}

	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}

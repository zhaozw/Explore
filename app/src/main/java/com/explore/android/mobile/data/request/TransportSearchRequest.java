package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class TransportSearchRequest implements ExBaseRequest {

	private String userId;
	private String token;
	private String outType;
	private String transportCode1;
	private String transprotCode2;
	private String warehouse;
	private String vatTo;
	private String transprotStartTime;
	private String transportEndTime;
	private int pSize;
	private int pageNum;
	
	// 2013-12-25 新增字段
	private String customerDeptId;
	private String projectId;
	private String vendorId;
	private String warehouseTo;
	private String shipTo;
	private String shipFrom;
	private String outCode1;
	private String outCode2;
	private String sts;
	private String isDd;
	private String remarks;

	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			json.put("USERID", userId);
			json.put("TOKEN", token);
			json.put("OUTTYPE", outType);
			json.put("TRANSPORTCODE1", transportCode1);
			json.put("TRANSPORTCODE2", transprotCode2);
			json.put("WAREHOUSE", warehouse);
			json.put("VATTO", vatTo);
			json.put("TRANSPORTSTARTTIME", transprotStartTime);
			json.put("TRANSPORTENDTIME", transportEndTime);
			json.put("PAGENUM", "" + pageNum);
			json.put("PAGESIZE", "" + pSize);
			
			json.put("CUSTOMERDEPTID", customerDeptId);
			json.put("VENDORID", vendorId);
			json.put("WAREHOUSETO", warehouseTo);
			json.put("SHIPTO", shipTo);
			json.put("SHIPFROM", shipFrom);
			json.put("OUTCODE1", outCode1);
			json.put("OUTCODE2", outCode2);
			json.put("STS", sts);
			json.put("ISDD", isDd);
			json.put("REMARKS", remarks);
			
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

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getTransportCode1() {
		return transportCode1;
	}

	public void setTransportCode1(String transportCode1) {
		this.transportCode1 = transportCode1;
	}

	public String getTransprotCode2() {
		return transprotCode2;
	}

	public void setTransprotCode2(String transprotCode2) {
		this.transprotCode2 = transprotCode2;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getVatTo() {
		return vatTo;
	}

	public void setVatTo(String vatTo) {
		this.vatTo = vatTo;
	}

	public String getTransprotStartTime() {
		return transprotStartTime;
	}

	public void setTransprotStartTime(String transprotStartTime) {
		this.transprotStartTime = transprotStartTime;
	}

	public String getTransportEndTime() {
		return transportEndTime;
	}

	public void setTransportEndTime(String transportEndTime) {
		this.transportEndTime = transportEndTime;
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

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getWarehouseTo() {
		return warehouseTo;
	}

	public void setWarehouseTo(String warehouseTo) {
		this.warehouseTo = warehouseTo;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getShipFrom() {
		return shipFrom;
	}

	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
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

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getIsDd() {
		return isDd;
	}

	public void setIsDd(String isDd) {
		this.isDd = isDd;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}

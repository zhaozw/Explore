package com.explore.android.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

public class OutLine {

	private int outLineId;
	
	private String outId;
	
	private String productId;
	
	private String partNo;
	
	private String npartNo;
	
	private String productName;
	
	private String whs;
	
	private String outType;
	
	private String logon;
	
	private String outQty1;
	
	private String qtyUnit;
	
	private String soPrice;
	
	private String priceUnit;
	
	private String remarks;
	
	public String toJsonString(){
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("OUTID", outId);
			json.put("PRODUCTID", productId);
			json.put("PARTNO", partNo);
			json.put("NPARTNO", npartNo);
			json.put("PRODUCTNAME", productName);
			json.put("WHS", whs);
			json.put("OUTTYPE", outType);
			json.put("LOGON", logon);
			json.put("OUTQTY1", outQty1);
			json.put("QTYUNIT", qtyUnit);
			json.put("SOPRICE", soPrice);
			json.put("PRICEUNIT", priceUnit);
			json.put("REMARKS", remarks);
			
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public int getOutLineId() {
		return outLineId;
	}

	public void setOutLineId(int outLineId) {
		this.outLineId = outLineId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getNpartNo() {
		return npartNo;
	}

	public void setNpartNo(String npartNo) {
		this.npartNo = npartNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getWhs() {
		return whs;
	}

	public void setWhs(String whs) {
		this.whs = whs;
	}

	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public String getOutQty1() {
		return outQty1;
	}

	public void setOutQty1(String outQty1) {
		this.outQty1 = outQty1;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(String soPrice) {
		this.soPrice = soPrice;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

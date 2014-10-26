package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class StockSearchRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String warehouseId;
	private String projectId;
	private String partNo;
	private String npartNo;
	private String proName;
	private String prolocation;
	private String logon;
	private String whs;
	private int psize;
	private int pageNum;
	
	// 2013-12-25 新增字段
	private String poCode1;
	private String poCode2;
	private String inCode1;
	private String inCode2;
	private String stockTimeStart;
	private String stockTimeEnd;
	private String beginDateStart;
	private String beginDateEnd;
	private String endDateStart;
	private String endDateEnd;
	
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();
		
		try {
			json.put("USERID", userId);
			json.put("TOKEN", token);
			json.put("WAREHOUSEID", warehouseId);
			json.put("PROJECTID", projectId);
			json.put("PARTNO", partNo);
			json.put("NPARTNO", npartNo);
			json.put("PRODUCTNAME", proName);
			json.put("LOC", prolocation);
			json.put("WHS", whs);
			json.put("LOGON", logon);
			json.put("PAGESIZE", ""+psize);
			json.put("PAGENUM", ""+pageNum);
			
			json.put("POCODE1", poCode1);
			json.put("POCODE2", poCode2);
			json.put("INCODE1", inCode1);
			json.put("INCODE2", inCode2);
			json.put("STOCKTIMESTART", stockTimeStart);
			json.put("STOCKTIMEEND", stockTimeEnd);
			json.put("BEGINDATESTART", beginDateStart);
			json.put("BEGINDATEEND", beginDateEnd);
			json.put("ENDDATESTART", endDateStart);
			json.put("ENDDATEEND", endDateEnd);
			
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

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProlocation() {
		return prolocation;
	}

	public void setProlocation(String prolocation) {
		this.prolocation = prolocation;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public String getWhs() {
		return whs;
	}

	public void setWhs(String whs) {
		this.whs = whs;
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

	public String getPoCode1() {
		return poCode1;
	}

	public void setPoCode1(String poCode1) {
		this.poCode1 = poCode1;
	}

	public String getPoCode2() {
		return poCode2;
	}

	public void setPoCode2(String poCode2) {
		this.poCode2 = poCode2;
	}

	public String getInCode1() {
		return inCode1;
	}

	public void setInCode1(String inCode1) {
		this.inCode1 = inCode1;
	}

	public String getInCode2() {
		return inCode2;
	}

	public void setInCode2(String inCode2) {
		this.inCode2 = inCode2;
	}

	public String getStockTimeStart() {
		return stockTimeStart;
	}

	public void setStockTimeStart(String stockTimeStart) {
		this.stockTimeStart = stockTimeStart;
	}

	public String getStockTimeEnd() {
		return stockTimeEnd;
	}

	public void setStockTimeEnd(String stockTimeEnd) {
		this.stockTimeEnd = stockTimeEnd;
	}

	public String getBeginDateStart() {
		return beginDateStart;
	}

	public void setBeginDateStart(String beginDateStart) {
		this.beginDateStart = beginDateStart;
	}

	public String getBeginDateEnd() {
		return beginDateEnd;
	}

	public void setBeginDateEnd(String beginDateEnd) {
		this.beginDateEnd = beginDateEnd;
	}

	public String getEndDateStart() {
		return endDateStart;
	}

	public void setEndDateStart(String endDateStart) {
		this.endDateStart = endDateStart;
	}

	public String getEndDateEnd() {
		return endDateEnd;
	}

	public void setEndDateEnd(String endDateEnd) {
		this.endDateEnd = endDateEnd;
	}

}

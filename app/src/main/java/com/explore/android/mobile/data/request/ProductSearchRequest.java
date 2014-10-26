package com.explore.android.mobile.data.request;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductSearchRequest implements ExBaseRequest{

	private String userId;
	private String token;
	private String proname;
	private String prono;
	private String nprono;
	private String customerDeptId;
	private String projectId;
	private String sts;
	private int psize;
	private int pageNum;
	
	@Override
	public String toJsonString() {
		JSONObject json = new JSONObject();

		try {

			json.put("USERID", userId);
			
			json.put("TOKEN", token);

			json.put("PARTNO", prono);

			json.put("NPARTNO", nprono);

			json.put("PRODUCTNAME", proname);
			
			json.put("CUSTOMERDEPTID", customerDeptId);
			
			json.put("PROJECTID", projectId);
			
			json.put("STS", sts);
			
			json.put("PAGESIZE", "" + psize);
			
			json.put("PAGENUM", "" + pageNum);
			
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

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getProno() {
		return prono;
	}

	public void setProno(String prono) {
		this.prono = prono;
	}

	public String getNprono() {
		return nprono;
	}

	public void setNprono(String nprono) {
		this.nprono = nprono;
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

	public String getCustomerDeptId() {
		return customerDeptId;
	}

	public void setCustomerDeptId(String customerDeptId) {
		this.customerDeptId = customerDeptId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}

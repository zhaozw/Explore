package com.explore.android.mobile.model;

public class ExDept {

	private String deptName;
	
	private String deptId;
	
	public ExDept(){
	}
	
	public ExDept(String name,String id){
		this.deptId = id;
		this.deptName = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}

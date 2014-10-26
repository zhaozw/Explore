package com.explore.android.mobile.model;

public class ExDeptEmp {

	private String empName;
	
	private String empId;
	
	public ExDeptEmp(){
	}
	
	public ExDeptEmp(String name,String id){
		this.empId = id;
		this.empName = name;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

}

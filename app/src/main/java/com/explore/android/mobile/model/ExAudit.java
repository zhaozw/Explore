package com.explore.android.mobile.model;

public class ExAudit {

	private int auditId;
	private int createById;
	private String billCode;
	private String sts;
	private String content;
	private String createByName;
	private String createByTime;
	private String billType;
	private String dept;

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public int getCreateById() {
		return createById;
	}

	public void setCreateById(int createById) {
		this.createById = createById;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateByTime() {
		return createByTime;
	}

	public void setCreateByTime(String createByTime) {
		this.createByTime = createByTime;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}

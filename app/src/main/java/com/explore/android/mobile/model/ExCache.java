package com.explore.android.mobile.model;

import java.util.Date;

public class ExCache {

	private int cacheId;
	private String cacheCode;
	private int chacheFor;
	private String data;
	private String dataType;
	private Date time;
	
	public ExCache(){
		chacheFor = -1;
	}

	public int getCacheId() {
		return cacheId;
	}

	public void setCacheId(int cacheId) {
		this.cacheId = cacheId;
	}

	public String getCacheCode() {
		return cacheCode;
	}

	public void setCacheCode(String cacheCode) {
		this.cacheCode = cacheCode;
	}

	public int getChacheFor() {
		return chacheFor;
	}

	public void setChacheFor(int chacheFor) {
		this.chacheFor = chacheFor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}

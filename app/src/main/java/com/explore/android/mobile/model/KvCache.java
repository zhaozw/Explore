package com.explore.android.mobile.model;

import java.util.Date;

public class KvCache {

	private long kvId;
	private String key;
	private int keyRes;
	private String value;
	private String type;
	private int times;
	private Date lastTime;
	private String remarks;

	public long getKvId() {
		return kvId;
	}

	public void setKvId(long kvId) {
		this.kvId = kvId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getKeyRes() {
		return keyRes;
	}

	public void setKeyRes(int keyRes) {
		this.keyRes = keyRes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

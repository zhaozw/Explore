package com.explore.android.mobile.model;

import com.explore.android.core.model.TimeRange;
import com.explore.android.core.util.DateUtil;

public class Schedule {

	private int timeNum;
	private TimeRange time;
	private int scheLineId;
	private String title;
	private String des;
	private String isFree;

	public int getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(int timeNum) {
		this.timeNum = timeNum;
	}

	public TimeRange getTime() {
		return time;
	}

	public void setTime(int hour) {
		this.time = DateUtil.getTimeRange(hour);
	}

	public int getScheLineId() {
		return scheLineId;
	}

	public void setScheLineId(int scheLineId) {
		this.scheLineId = scheLineId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

}

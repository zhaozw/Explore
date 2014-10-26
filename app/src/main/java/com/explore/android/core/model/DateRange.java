package com.explore.android.core.model;

import java.util.Date;

public class DateRange {

	private Date beginDate;

	private Date endDate;

	public DateRange() {
	}

	public DateRange(Date bd, Date ed) {
		this.beginDate = bd;
		this.endDate = ed;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

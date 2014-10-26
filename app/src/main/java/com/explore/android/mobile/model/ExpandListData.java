package com.explore.android.mobile.model;

import java.util.List;

public class ExpandListData {

	private String id;
	
	private String id2;
	
	private String title1;
	
	private String title2;
	
	private String title3;
	
	private String title4;
	
	private List<DetailInfo> detailList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle1() {
		return title1;
	}
	
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	
	public String getTitle2() {
		return title2;
	}
	
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	
	public String getTitle3() {
		return title3;
	}
	
	public void setTitle3(String title3) {
		this.title3 = title3;
	}
	
	public List<DetailInfo> getDetailList() {
		return detailList;
	}
	
	public void setDetailList(List<DetailInfo> detailList) {
		this.detailList = detailList;
	}

	public String getTitle4() {
		return title4;
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}
	
}

package com.explore.android.mobile.data.predata;

public class AsynPreData {

	private String url;
	private String jsonTitle;
	private String jsonName;
	private String jsonValue;
	private String type;
	private int titleRes;
	private String[] extraParams;
	private String[] extraDatas;
	
	public AsynPreData() {
	}

	public AsynPreData(String url, String jsonTitle, String jsonName,
			String jsonValue, int titleRes, String type) {
		this.url = url;
		this.jsonTitle = jsonTitle;
		this.jsonName = jsonName;
		this.jsonValue = jsonValue;
		this.type = type;
		this.titleRes = titleRes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonTitle() {
		return jsonTitle;
	}

	public void setJsonTitle(String jsonTitle) {
		this.jsonTitle = jsonTitle;
	}

	public String getJsonName() {
		return jsonName;
	}

	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public int getTitleRes() {
		return titleRes;
	}

	public void setTitleRes(int titleRes) {
		this.titleRes = titleRes;
	}

	public String[] getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(String[] extraParams) {
		this.extraParams = extraParams;
	}

	public String[] getExtraDatas() {
		return extraDatas;
	}

	public void setExtraDatas(String[] extraDatas) {
		this.extraDatas = extraDatas;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

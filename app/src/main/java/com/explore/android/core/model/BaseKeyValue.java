package com.explore.android.core.model;

public class BaseKeyValue {

	private String key;
	private String value;
	private int keyResourceId;
	private String[] extraData;
	
	public BaseKeyValue(){
	}
	
	public BaseKeyValue(int keyId, String value){
		this.keyResourceId = keyId;
		this.value = value;
		this.key = "";
	}

	public BaseKeyValue(String key, String value){
		this.key = key;
		this.value = value;
		this.keyResourceId = -1;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getKeyResourceId() {
		return keyResourceId;
	}

	public void setKeyResourceId(int keyResourceId) {
		this.keyResourceId = keyResourceId;
	}

	public String[] getExtraData() {
		return extraData;
	}

	public void setExtraData(String[] extraData) {
		this.extraData = extraData;
	}
	
}

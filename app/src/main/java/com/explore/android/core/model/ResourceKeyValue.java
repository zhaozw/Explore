package com.explore.android.core.model;

public class ResourceKeyValue {
	private String key;
	private int value;
	
	public ResourceKeyValue() {
	}

	public ResourceKeyValue(String k, int v) {
		this.key = k;
		this.value = v;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}

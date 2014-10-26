package com.explore.android.mobile.model;

import java.util.List;

public class SingleMenuItem {
	private int id;
	private int code;
	private String name;
	private int drawableId;
	private String actionName;
	
	private List<SingleMenuItem> groups;
	
	public SingleMenuItem(){
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDrawableId() {
		return drawableId;
	}

	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<SingleMenuItem> getGroups() {
		return groups;
	}

	public void setGroups(List<SingleMenuItem> groups) {
		this.groups = groups;
	}
}

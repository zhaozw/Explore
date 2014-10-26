package com.explore.android.mobile.model;

import java.io.Serializable;

import com.explore.android.mobile.common.PermissionManage;

public class Permission implements Serializable{

	private static final long serialVersionUID = 1L;

	private int permissionId;
	
	private int permissionCode;
	
	private String permissionName;
	
	private String permissionIcon;
	
	private String activityName;
	
	private String haspermission;
	
	private int permissionLevel;
	
	private String  fatherCode;
	
	public int getDrawableIconId(){
		PermissionManage perManage = PermissionManage.getInstance();
		return perManage.getDrawableIdByFieldName(getPermissionIcon());
	}

	public int getPermissionId() {
		return permissionId;
	}

	public int getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(int permissionCode) {
		this.permissionCode = permissionCode;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionIcon() {
		return permissionIcon;
	}

	public void setPermissionIcon(String permissionIcon) {
		this.permissionIcon = permissionIcon;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getHaspermission() {
		return haspermission;
	}

	public void setHaspermission(String haspermission) {
		this.haspermission = haspermission;
	}

	public int getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getFatherCode() {
		return fatherCode;
	}

	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

}

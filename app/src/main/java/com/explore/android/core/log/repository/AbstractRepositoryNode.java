package com.explore.android.core.log.repository;


public abstract class AbstractRepositoryNode {
	protected String name;
	
	protected AbstractRepositoryNode() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

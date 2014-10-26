package com.explore.android.core.model;

public class ExAppErrorLog {

	private String errorTime;
	private String errorApp;
	private String errorTag;
	private String errorOwner;
	private StackTraceElement[] stack;
	
	public String getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public String getErrorApp() {
		return errorApp;
	}

	public void setErrorApp(String errorApp) {
		this.errorApp = errorApp;
	}

	public String getErrorTag() {
		return errorTag;
	}

	public void setErrorTag(String errorTag) {
		this.errorTag = errorTag;
	}

	public String getErrorOwner() {
		return errorOwner;
	}

	public void setErrorOwner(String errorOwner) {
		this.errorOwner = errorOwner;
	}

	public StackTraceElement[] getStack() {
		return stack;
	}

	public void setStack(StackTraceElement[] stack) {
		this.stack = stack;
	}
	
	
}

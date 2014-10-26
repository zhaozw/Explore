package com.explore.android.core.base;

public abstract class BaseDBO {
	
	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	
	public abstract BaseDBO open();

	public abstract void close();
}

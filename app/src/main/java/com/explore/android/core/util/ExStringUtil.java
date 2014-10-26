package com.explore.android.core.util;

public class ExStringUtil {

	public static boolean isResNoBlank(String str){
		if(str != null && !"null".equals(str.trim()) && !"".equals(str.trim())){
			return true;
		}
		return false;
	}
}

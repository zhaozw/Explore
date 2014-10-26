package com.explore.android.mobile.common;

public class UrlUtils {

	public static String combinUrl(String url, String requestName) {
		StringBuilder builder = new StringBuilder("http://");
		builder.append(url);
		builder.append("/");
		builder.append(requestName);
		return builder.toString();
	}
}

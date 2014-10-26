package com.explore.android.mobile.common;

import java.util.ArrayList;
import java.util.List;

public class PermissionLevel {

	private String L1;

	private String L2;

	private String L3;

	public PermissionLevel(String l1, String l2, String l3) {
		this.L1 = l1;
		this.L2 = l2;
		this.L3 = l3;
	}

	public String[] getL1Codes() {
		return split(L1, 1);
	}

	public String[] getL2Codes() {
		return split(L2, 2);
	}

	public String[] getL3Codes() {
		return split(L3, 3);
	}

	public List<String> getAllCodes() {
		List<String> codes = new ArrayList<String>();

		String[] l1 = getL1Codes();
		String[] l2 = getL2Codes();
		String[] l3 = getL3Codes();

		if (l1 != null) {
			for (int i = 0; i < l1.length; i++) {
				codes.add(l1[i]);
			}
		}
		
		if (l2 != null) {
			for (int i = 0; i < l2.length; i++) {
				codes.add(l2[i]);
			}
		}
		
		if (l3 != null) {
			for (int i = 0; i < l3.length; i++) {
				codes.add(l3[i]);
			}
		}

		return codes;
	}

	private String[] split(String str, int digit) {
		if (str.length() % digit == 0) {
			int length = str.length() / digit;
			String[] strs = new String[length];
			for (int i = 0; i < length; i++) {
				strs[i] = left(str, digit);
				str = substring(str, digit, str.length());
			}
			return strs;
		} else {
			return null;
		}

	}

	private String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	private String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return "";
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	public String getL1() {
		return L1;
	}

	public void setL1(String l1) {
		L1 = l1;
	}

	public String getL2() {
		return L2;
	}

	public void setL2(String l2) {
		L2 = l2;
	}

	public String getL3() {
		return L3;
	}

	public void setL3(String l3) {
		L3 = l3;
	}

}

package com.explore.android.core.util;

import java.util.Calendar;
import java.util.Date;

public class CalenderUtil {
	
	private static final Calendar cal = Calendar.getInstance();
	
	public CalenderUtil(){
	}
	
	public CalenderUtil(Date date){
		cal.setTime(date);
	}
	
	public CalenderUtil(int year, int month, int day){
		cal.set(year, month, day);
	}
	
}

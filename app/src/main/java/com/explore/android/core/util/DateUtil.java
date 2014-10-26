package com.explore.android.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.util.Log;

import com.explore.android.core.model.DateRange;
import com.explore.android.core.model.TimeRange;

public class DateUtil {

	@SuppressLint("SimpleDateFormat")
	public static final DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
	@SuppressLint("SimpleDateFormat")
	public static final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final long temp = 86400000L; // 表示一天

	public static Date getNow() {
		return new Date();
	}
	
	/**
	 * 获取日期字符串（不带时间）
	 * 
	 * @param date
	 * @return
	 */
	public static String DateFormatWithoutDayTime(Date date) {
		return df1.format(date);
	}
	
	/**
	 * 获取日期字符串（根据年月日）
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatWithDateNum(int y, int m, int d) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar. MONTH , m-1);
		cal.set(Calendar. DAY_OF_MONTH , d);
		return df1.format(cal.getTime());
	}

	/**
	 * 获取日期字符串（带时间）
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatWithDayTime(Date date) {
		return df2.format(date);
	}

	/**
	 * 根据年月日信息获取该日期所在周的周一和周日的日期信息
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static DateRange getWeekByDate(int year, int month, int day) {

		if (year <= 0) {
			return null;
		}

		if (month < 1 || month > 12) {
			return null;
		}

		int local_days = getMonthDaysCount(year, month);

		if (day < 1 || day > local_days) {
			return null;
		}

		DateRange dr = new DateRange();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		int daysOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (daysOfWeek == 1) {
			dr.setBeginDate((new Date(cal.getTimeInMillis() - 6 * temp))); // 周一的日期
			dr.setEndDate((new Date(cal.getTimeInMillis()))); // 周日的日期
		} else {
			dr.setBeginDate(new Date(cal.getTimeInMillis() - (daysOfWeek - 2)
					* temp)); // 周一的日期
			dr.setEndDate(new Date(cal.getTimeInMillis() + (8 - daysOfWeek)
					* temp)); // 周日的日期
		}

		return dr;
	}

	/**
	 * 根据年和月获取该月的第一天和该月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static DateRange getMonthByYM(int year, int month) {

		if (year <= 0) {
			return null;
		}
		if (month < 1 || month > 12) {
			return null;
		}

		DateRange dr = new DateRange();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		dr.setBeginDate(cal.getTime()); // 该月的最后一天

		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		dr.setEndDate(cal.getTime()); // 该月的最后一天

		return dr;
	}

	/**
	 * 根据 "yyyy-MM-dd HH:mm:ss"格式的数据，拆分成当前日期 并取得当前是周几
	 * 
	 * @param dateStr
	 * @return
	 */
	public static int getWeekDayByDateString(String dateStr) {

		int year = 0;
		int month = 0;
		int day = 0;
		int weekday = -1;

		try {
			String a[] = dateStr.split(" ");
			String b[] = a[0].split("-");

			year = Integer.parseInt(b[0]);
			month = Integer.parseInt(b[1]);
			day = Integer.parseInt(b[2]);

		} catch (Exception e) {
			Log.e("Monitor Database Date transform exception", e.toString());
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);

		weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;

		if (weekday == 0) {
			weekday = 7;
		}

		return weekday;
	}

	/**
	 * 根据 "yyyy-MM-dd HH:mm:ss"格式的数据，拆分成当前日期 并取得当前是周几
	 * 
	 * @param dateStr
	 * @return
	 */
	public static int getMonthDayByDateString(String dateStr) {
		int year = 0;
		int month = 0;
		int day = 0;
		int monthDay = -1;
		try {
			String a[] = dateStr.split(" ");
			String b[] = a[0].split("-");

			year = Integer.parseInt(b[0]);
			month = Integer.parseInt(b[1]);
			day = Integer.parseInt(b[2]);

		} catch (Exception e) {
			Log.e("Monitor Database Date transform exception", e.toString());
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);

		monthDay = cal.get(Calendar.DAY_OF_MONTH);
		return monthDay;
	}

	/**
	 * 根据日期获取该日期所在星期的每天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static HashMap<String, Date> getWeekDays(Date date) {

		HashMap<String, Date> map = new HashMap<String, Date>();

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < 6; i++) {
			cal.set(Calendar.DAY_OF_WEEK, i + 2);
			map.put("day" + i, cal.getTime());
		}

		cal.set(Calendar.DAY_OF_WEEK, 1);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		map.put("day6", cal.getTime());

		return map;
	}

	/**
	 * 根据年和月份获取当月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDaysCount(int year, int month) {
		int day_num = 0;

		switch (month) {
		case 1:
			day_num = 31;
			break;
		case 2:
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
				day_num = 29;
			} else {
				day_num = 28;
			}
			break;
		case 3:
			day_num = 31;
			break;
		case 4:
			day_num = 30;
			break;
		case 5:
			day_num = 31;
			break;
		case 6:
			day_num = 30;
			break;
		case 7:
			day_num = 31;
			break;
		case 8:
			day_num = 31;
			break;
		case 9:
			day_num = 30;
			break;
		case 10:
			day_num = 31;
			break;
		case 11:
			day_num = 30;
			break;
		case 12:
			day_num = 31;
			break;

		default:
			break;
		}
		return day_num;
	}

	/**
	 * 根据月份获取月份名称
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month) {

		if (month > 12 || month < 1) {
			return "NULL";
		}

		String monName = "NULL";

		switch (month) {
		case 1:
			monName = "January";
			break;
		case 2:
			monName = "February";
			break;
		case 3:
			monName = "March";
			break;
		case 4:
			monName = "April";
			break;
		case 5:
			monName = "May";
			break;
		case 6:
			monName = "June";
			break;
		case 7:
			monName = "July";
			break;
		case 8:
			monName = "August";
			break;
		case 9:
			monName = "September";
			break;
		case 10:
			monName = "October";
			break;
		case 11:
			monName = "November";
			break;
		case 12:
			monName = "December";
			break;
		default:
			break;
		}
		return monName;
	}

	/**
	 * 根据时间获取时间字符串
	 * 
	 * @param time
	 * @return
	 */
	public static TimeRange getTimeRange(int time) {
		TimeRange tRange = new TimeRange();

		if (time > 23 || time < 0) {
			tRange = null;
		}

		if (time < 10) {
			tRange.setBeginTime("0" + time + ":00");
			if (time == 9) {
				tRange.setEndTime((time + 1) + ":00");
			} else {
				tRange.setEndTime("0" + (time +1) + ":00");
			}
		} else {
			tRange.setBeginTime(time + ":00");
			tRange.setEndTime((time + 1) + ":00");
		}

		return tRange;
	}

	/**
	 * 检查缓存时间,如果超过规定的时间,则返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean checkCacheTime(Date date1, Date date2, long timenum) {
		long dif = date2.getTime() - date1.getTime();
		if (dif < 0) {
			dif = 0 - dif;
		}
		if (dif > timenum) {
			return true;
		} else {
			return false;
		}
	}

}

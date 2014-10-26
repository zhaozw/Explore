package com.explore.android.mobile.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * 断轮询服务管理
 * @author Ryan
 *
 */
public class PollingManage {
	
	public static boolean STATU = false;

	public static void startPollingService(Context context, int seconds, Class<?> cls,String action) {
		
		//获取AlarmManager系统服务
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		//包装需要执行Service的Intent
		Intent intent = new Intent(context, cls);
		intent.setAction(action);
		intent.putExtra(P2PService.PARAM, "polling");
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//触发服务的起始时间
		long triggerAtTime = SystemClock.elapsedRealtime();
		
		//使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, seconds*1000, pendingIntent);
		
		STATU = true;
	}
	
	 public static void stopPollingService(Context context, Class<?> cls,String action){
		 
		 AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		 
		 Intent intent = new Intent(context, cls);
	     intent.setAction(action);  
	     PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
	     
	     //取消正在执行的服务  
	     alarmManager.cancel(pendingIntent);
	     
	     STATU = false;
	     
	     Log.e("PollingManage", "stopPollingService");
	 }
}

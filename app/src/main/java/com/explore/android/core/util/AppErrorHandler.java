package com.explore.android.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.explore.android.mobile.constants.AppConstant;
import com.explore.android.mobile.data.cache.GlobalData;


public class AppErrorHandler implements Thread.UncaughtExceptionHandler {

	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private static AppErrorHandler instance;
	
	private static final long LOG_MAX_SIZE = 3 * 1024 * 1024;

	private AppErrorHandler() {
	}

	public static AppErrorHandler getInstance() {
		if (instance == null) {
			instance = new AppErrorHandler();
		}
		return instance;
	}

	public void init(Context context) {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			Log.i("uncaughtException", ""+ex.getMessage());
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}
	
	private static final String LOG_DIR = AppConstant.APP_HOME_PATH +"/" + AppConstant.APP_LOG_DIR;
	private static final String ERR_LOG_PATH = LOG_DIR + "/"+ AppConstant.APP_LOG_CRASHLOG;

	private boolean handleException(final Throwable ex) {
		
		if (AppConstant.DEBUG_STATU) {
			if (ex == null) {
				return false;
			}
			final StackTraceElement[] stack = ex.getStackTrace();
			final String message = ex.getMessage()+"\n";
			new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					createLogDirectory();
					File file = new File(ERR_LOG_PATH);
					try {
						FileOutputStream fos = new FileOutputStream(file, true);
						fos.write(getLogHeader().getBytes());
						fos.write(message.getBytes());
						for (int i = 0; i < stack.length; i++) {
							String temp = stack[i].toString()+"\n";
							fos.write(temp.getBytes());
						}
						fos.flush();
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Looper.loop();
				}
			}.start();
		}
		return false;
	}

	private void createLogDirectory() {
		File file = new File(LOG_DIR);
		File errorFile = new File(ERR_LOG_PATH);
		try {
			if (!file.exists() || !file.isDirectory()) {
				file.mkdir();
			}
			if(errorFile.exists() && errorFile.getTotalSpace() > LOG_MAX_SIZE) {
				errorFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLogHeader() {
		StringBuffer log_header = new StringBuffer();
		String line = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n";
		String log_time = "Log Time: " + getLogDate()+"\n";
		String app_name = "Application: " + AppConstant.APP_PACKAGE_NAME+"\n";
		String userId = "User ID:" + GlobalData.userId + "\n";
		log_header.append(line);
		log_header.append(app_name);
		log_header.append(log_time);
		log_header.append(userId);
		return log_header.toString();
	}

	@SuppressLint("SimpleDateFormat")
	private String getLogDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		return format.format(curDate);
	}

}

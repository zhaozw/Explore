package com.explore.android.mobile.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MsgBroadcastReceiver extends BroadcastReceiver{

	public static final String ACTION = "com.explore.android.mobile.service.MSGRECEIVER";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().endsWith(ACTION)){
			
		}
	}
}

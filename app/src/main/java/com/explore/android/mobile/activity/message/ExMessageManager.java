package com.explore.android.mobile.activity.message;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.explore.android.mobile.db.opration.MessageDBO;
import com.explore.android.mobile.model.ExMessage;


public class ExMessageManager {

	public static boolean UPDATA = false;
	public static List<ExMessage> msgList;
	
	public static List<ExMessage> loadMessages(Context con){
		
		if(msgList == null){
			msgList = new ArrayList<ExMessage>();
		} else {
			msgList.clear();
		}
		
		MessageDBO dbo = new MessageDBO(con);
		dbo.open();
		msgList = dbo.getMessages();
		dbo.close();
		
		return msgList;
	}
}

package com.explore.android.core.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.RequestConstants;

public class HttpHelper {
	
	public static ExResponse submit(HashMap<String, String> data){
		ExResponse response = new ExResponse();
		HttpConn conn = HttpConn.getInstance();
		conn.setReqURLStr(getUrl(data.get(RequestConstants.REQURL),data.get(RequestConstants.REQNAME)));
		conn.setReqTimeOut(6000);
		if(data.size() > 3){
			int length = data.size() - 2;
			List<String> requests = new ArrayList<String>();
			for (int i = 0; i < length; i++) {
				String dataName;
				if(0 == i){
					dataName = RequestConstants.REQDATA;
				} else {
					dataName = RequestConstants.REQDATA + i;
				}
                if (AppStatus.IS_DEBUG_MODE) {
                    Log.e("HttpHelper", "Request:" + dataName + "=" + data.get(dataName));
                }
				requests.add(data.get(dataName));
			}
			conn.setReqParaStr(requests);
			
		} else {
			conn.setReqParaStr(data.get(RequestConstants.REQDATA));
            if (AppStatus.IS_DEBUG_MODE) {
                Log.e("HttpHelper", "Request:" + data.get(RequestConstants.REQDATA));
            }
		}
		
		response.setResCode(conn.execute());
		response.setResMessage(conn.getExecResultStr());
		return response;
	}
	
	public static String simpleConn(String url,String request,String data){
		HttpConn conn = HttpConn.getInstance();
		return conn.simpleAction(getUrl(url, request), data);
	}
	
	public static ExResponse asynData(HashMap<String, String> data){
        if (AppStatus.IS_DEBUG_MODE) {
            Log.e("HttpHelper", "Request:" + data.get(RequestConstants.REQDATA));
        }
		ExResponse response = new ExResponse();
		HttpConn conn = HttpConn.getInstance();
		conn.setReqURLStr(getUrl(data.get(RequestConstants.REQURL),data.get(RequestConstants.REQNAME)));
		conn.setReqTimeOut(8000);
		conn.setReqParaStr(data.get(RequestConstants.REQDATA));
		response.setResCode(conn.execute());
		response.setResMessage(conn.getExecResultStr());
		return response;
	}
	
	public static void close(){
		HttpConn conn = HttpConn.getInstance();
		conn.destory();
	}
	
	private static String getUrl(String url,String requestName) {
		return "http://" + url + "/" + requestName;
	}

}

package com.explore.android.core.http;

import java.util.HashMap;

import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.constants.RequestConstants;

public class SubmitTask extends BaseAsynTask<String, Void, ExResponse>{

	@Override
	protected ExResponse doInBackground(String... params) {
		
		if(params.length >0){
			ExResponse response = null;
			HashMap<String, String> lgparams = new HashMap<String, String>();
			lgparams.put(RequestConstants.REQURL, params[0]);
			lgparams.put(RequestConstants.REQNAME, params[1]);
			lgparams.put(RequestConstants.REQDATA, params[2]);
			
			if(params.length > 3){
				for (int i = 3; i < params.length; i++) {
					lgparams.put(RequestConstants.REQDATA + (4-i), params[i]);
				}
			}
			
			/*
			if(Common.isServerAvailable(params[0])){
				response = HttpHelper.submit(lgparams);
			}else{
				response = new ExResponse();
				response.setResMessage("{\"STATUS\":\"CONNFAILED\"}");
			}
			*/
			response = HttpHelper.submit(lgparams);
			
			return response;
		}
		return null;
	}
	

}

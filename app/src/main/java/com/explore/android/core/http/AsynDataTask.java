package com.explore.android.core.http;

import java.util.HashMap;

import com.explore.android.core.base.BaseAsynTask;
import com.explore.android.core.model.ExResponse;
import com.explore.android.mobile.constants.RequestConstants;

public class AsynDataTask extends BaseAsynTask<String, Void, ExResponse>{

	@Override
	public ExResponse doInBackground(String... params) {
		if(params.length >0){
			ExResponse response = null;
			HashMap<String, String> lgparams = new HashMap<String, String>();
			lgparams.put(RequestConstants.REQURL, params[0]);
			lgparams.put(RequestConstants.REQNAME, params[1]);
			lgparams.put(RequestConstants.REQDATA, params[2]);
			response = HttpHelper.asynData(lgparams);
			
			return response;
		}
		return null;
	}
	
	@Override
	public void onCancelled() {
		HttpHelper.close();
	}

}

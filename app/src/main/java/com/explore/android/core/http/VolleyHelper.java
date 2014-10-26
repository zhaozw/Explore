package com.explore.android.core.http;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.explore.android.R;
import com.explore.android.core.util.Base64Coder;

public class VolleyHelper {

	private static final String TAG = "VolleyHelper";
	public static RequestQueue mRequestQueue = null;
	private Listener<JSONObject> responseListener;
	private ErrorListener errorListener;
	private Context context;

	public VolleyHelper(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}
		this.context = context;
	}

	public boolean httpRequest(String... params) {
		if (params.length > 2) {
			HashMap<String, String> requestDatas = new HashMap<String, String>();
			if (params.length > 2) {
				for (int i = 2; i < params.length; i++) {
					String data = encodeData(params[i]);
					if (data == null) {
						showErrorMessage(R.string.msg_request_encode_error);
						return false;
					}
					if (2 == i) {
						requestDatas.put("DATA", data);
					} else {
						requestDatas.put("DATA" + (3 - i), data);
					}
				}
			}
			VolleyJsonRequest request = new VolleyJsonRequest(params[0], params[1],
					responseListener, errorListener, requestDatas);
			mRequestQueue.add(request);
			return true;
		}
		showErrorMessage(R.string.msg_request_data_error);
		return false;
	}
	
	private String encodeData(String data) {
		try {
			return Base64Coder.encode(data);
		} catch (Exception e) {
			Log.e(TAG, "Base64Coder encode error.");
		}
		return null;
	}
	
	private void showErrorMessage(int msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public void setOnResponseListener(Listener<JSONObject> responseListener) {
		this.responseListener = responseListener;
	}

	public void setOnErrorListener(ErrorListener errorListener) {
		this.errorListener = errorListener;
	}

}

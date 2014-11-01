package com.explore.exapp.base.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 */
public class VolleyJsonRequest extends Request<JsonObject> {

    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
        String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);
	
    // 向服务器端发送的数据
	private Map<String, String> requestMap;
	
	// 服务器响应的处理监听
	private final Listener<JsonObject> mListener;

	/**
	 * 固定使用Post方式
	 * 
	 * @param url 服务器地址
	 * @param listener 服务器响应的监听
	 * @param errorListener 请求失败的监听
	 * @param map 向服务端发送的数据
	 */
	public VolleyJsonRequest(String url, Listener<JsonObject> listener, ErrorListener errorListener,
                             Map<String, String> map) {
		super(Method.POST, url, errorListener);
		this.requestMap = map;
		mListener = listener;
	}

	/**
	 * 向服务发送数据的Map（POST）
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if (requestMap != null) {
			return requestMap;
		}
		return super.getParams();
	}

	/**
	 * Request头（参考JsonRequest）
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("Charset", "UTF-8");
        return params;
	}

	/**
	 * 处理Response（参考JsonRequest）
	 * 使用Base64Coder解析返回的数据
	 */
	@Override
	protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String tempString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			String jsonString = Base64Coder.decode(tempString);
            JsonParser jsonParser = new JsonParser();
			return Response.success(jsonParser.parse(jsonString).getAsJsonObject(),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e1) {
			return Response.error(new ParseError(e1));
		} catch (JSONException e2) {
			return Response.error(new ParseError(e2));
		} catch (Exception e3) {
			return Response.error(new ParseError(e3));
		}
	}

	@Override
	protected void deliverResponse(JsonObject response) {
		mListener.onResponse(response);
	}
	
	@Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }
}

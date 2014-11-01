package com.explore.exapp.base.util;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.explore.exapp.R;
import com.explore.exapp.base.MyApplication;

import java.util.HashMap;

/**
 * Created by ryan on 14/10/31.
 */
public class HttpUtil {

    public static Builder request(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private int method;
        private Context mContext;
        private String url;
        private HashMap<String, String> parameters;
        private Response.Listener completeListener;
        private Response.ErrorListener errorListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setParaMeters(String name, String value) {
            if (parameters == null) {
                parameters = new HashMap<String, String>();
            }
            parameters.put(name, value);
            return this;
        }

        public Builder setCompleteListener(Response.Listener completeListener) {
            this.completeListener = completeListener;
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public void execute() {

            if (url == null || url.length() == 0) {
                ToastUtil.showToast(mContext, R.string.http_error_no_url);
                return;
            }

            if (completeListener == null) {
                ToastUtil.showToast(mContext, R.string.http_error_no_complete_listener);
                return;
            }

            if (parameters == null || parameters.size() == 0) {
                ToastUtil.showToast(mContext, R.string.http_error_no_parameters);
                return;
            }

            if (errorListener == null) {
                errorListener = defaultErrorListener;
            }

            VolleyJsonRequest volleyJsonRequest = new VolleyJsonRequest(url, completeListener,
                    errorListener, parameters);
            MyApplication.getRequestQueue().add(volleyJsonRequest);
        }
    }

    private static final Response.ErrorListener defaultErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };

    private static String encodeData(String data) {
        try {
            return Base64Coder.encode(data);
        } catch (Exception e) {
            LogUtil.error(e);
        }
        return null;
    }
}

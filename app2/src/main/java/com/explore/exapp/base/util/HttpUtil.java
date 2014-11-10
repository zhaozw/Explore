package com.explore.exapp.base.util;

import android.content.Context;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.explore.exapp.R;
import com.explore.exapp.base.MyApplication;
import com.explore.exapp.data.AppConstants;
import com.google.gson.JsonObject;

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

        private OnCompleteListener completeListener;
        private OnProgressFinishListener finishListener;
        private OnErrorListener errorListener;

        public Builder(Context context) {
            this.mContext = context;
            this.method = -2;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setParaMeters(String name, String value) {
            if (parameters == null) {
                parameters = new HashMap<String, String>();
            }
            try {
                value = Base64Coder.encode(value);
            } catch (Exception e) {
                LogUtil.error(e);
            }
            parameters.put(name, value);
            return this;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setCompleteListener(OnCompleteListener listener) {
            this.completeListener = listener;
            return this;
        }

        public Builder setProgressFinishListener(OnProgressFinishListener listener) {
            this.finishListener = listener;
            return this;
        }

        public void execute() {

            if (url == null || url.length() == 0) {
                ToastUtil.showToast(mContext, R.string.http_error_no_url);
                finishListener.onProgressFinish();
                return;
            }

            if (completeListener == null) {
                ToastUtil.showToast(mContext, R.string.http_error_no_complete_listener);
                finishListener.onProgressFinish();
                return;
            }

            if (parameters == null || parameters.size() == 0) {
                ToastUtil.showToast(mContext, R.string.http_error_no_parameters);
                finishListener.onProgressFinish();
                return;
            }

            if (method == -2) {
                method = Request.Method.POST;
            }

            if (errorListener == null) {
                errorListener = defaultErrorListener;
            }

            VolleyJsonRequest volleyJsonRequest = new VolleyJsonRequest(method, url, new Response.Listener<JsonObject>() {
                @Override
                public void onResponse(JsonObject jsonObject) {
                    finishListener.onProgressFinish();
                    if (jsonObject.has(AppConstants.HTTP_STATUS)) {
                        if (AppConstants.USEREXCEPTION.equals(jsonObject.get(AppConstants.HTTP_STATUS).getAsString())) {
                            String msg = jsonObject.get(AppConstants.EXCEPTIONLIST).getAsString();
                            DialogUtil.showMessageDialog(mContext, R.string.user_exception_title, msg,
                                    R.string.known, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        } else if (AppConstants.EXCEPTION.equals(jsonObject.get(AppConstants.HTTP_STATUS).getAsString())) {
                            ToastUtil.showToast(mContext, R.string.http_request_exception);
                        }
                        completeListener.onComplete(jsonObject);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    finishListener.onProgressFinish();
                }
            }, parameters);
            MyApplication.getRequestQueue().add(volleyJsonRequest);
        }
    }

    private static final OnErrorListener defaultErrorListener = new OnErrorListener() {
        @Override
        public void onError() {

        }
    };

    public interface OnProgressFinishListener {
        void onProgressFinish();
    }

    public interface OnCompleteListener {
        void onComplete(JsonObject jsonObject);
    }

    public interface OnErrorListener {
        void onError();
    }

    private static String encodeData(String data) {
        try {
            return Base64Coder.encode(data);
        } catch (Exception e) {
            LogUtil.error(e);
        }
        return null;
    }
}

package com.explore.android.mobile.data;

import org.json.JSONObject;

import android.util.Log;

import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.ResponseConstant;

public class HttpResponseTrans {

	private static final String TAG = "HttpResponseConstantTrans";

	private HttpResponseTrans() {
	}

	public static String trans(String retSrc) {

		if (retSrc.equals(ResponseConstant.TIMEOUT)) {

			return ResponseConstant.TIMEOUT;

		} else if (retSrc.equals(ResponseConstant.CONNERROR)) {

			return ResponseConstant.CONNERROR;

		} else if (retSrc.equals(ErrorConstants.APP_ERROR)) {

			return ErrorConstants.APP_ERROR;

		} else {

			try {

				JSONObject json = new JSONObject(retSrc);

				String status = json.getString("STATUS");

				Log.i(TAG, "STATU:" + status);

				if (ResponseConstant.OK.equals(status)) {

					return ResponseConstant.OK;

				} else if (ResponseConstant.LOGIN_NOUSER.equals(status)) {

					return ResponseConstant.LOGIN_NOUSER;

				} else if (ResponseConstant.LOGIN_PASSWORDERROE.equals(status)) {

					return ResponseConstant.LOGIN_PASSWORDERROE;

				} else if (ResponseConstant.LOGIN_NOBINDING.equals(status)) {

					return ResponseConstant.LOGIN_NOBINDING;

				} else if (ResponseConstant.LOGIN_NODEVICE.equals(status)) {

					return ResponseConstant.LOGIN_NODEVICE;

				} else if (ResponseConstant.EXCEPTION.equals(status)) {

					return ResponseConstant.EXCEPTION;

				} else if (ResponseConstant.DUPLICATED.equals(status)) {

					return ResponseConstant.DUPLICATED;

				} else if (ResponseConstant.LOCKUSER.equals(status)) {

					return ResponseConstant.LOCKUSER;

				} else if (ResponseConstant.LOCKDEVICE.equals(status)) {

					return ResponseConstant.LOCKDEVICE;

				} else {

					return ErrorConstants.APP_UNKNOW;

				}
			} catch (Exception e) {

				Log.e(TAG, e.toString());

				return ResponseConstant.JSONERROR;

			}
		}

	}
}

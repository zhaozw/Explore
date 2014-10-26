package com.explore.android.mobile.common;

import android.content.Context;
import android.widget.Toast;

import com.explore.android.R;
import com.explore.android.mobile.constants.ErrorConstants;
import com.explore.android.mobile.constants.ResponseConstant;

public class ExResponseMessage {

	private Context context;

	public ExResponseMessage(Context con) {
		this.context = con;
	}

	/**
	 * Transform the constant to Chinese text define in string file.
	 * @param result
	 */
	public void transformMsg(String result) {

		if (ResponseConstant.LOGIN_NOUSER.equals(result)) {

			showToast(context.getResources().getString(R.string.error_register_nouser).toString());

		}else if(ResponseConstant.CONNERROR.equals(result)){
			
			showToast(context.getResources().getString(R.string.error_conn_error).toString());
			
		} else if (ResponseConstant.LOGIN_NOBINDING.equals(result)) {

			showToast(context.getResources().getString(R.string.error_login_nobinding).toString());

		} else if (ResponseConstant.LOGIN_NODEVICE.equals(result)) {

			showToast(context.getResources().getString(R.string.error_login_nodevice).toString());

		} else if (ResponseConstant.LOGIN_PASSWORDERROE.equals(result)) {

			showToast(context.getResources().getString(R.string.error_login_pwderror).toString());

		} else if (ErrorConstants.APP_ERROR.equals(result)) {

			showToast(context.getResources().getString(R.string.error_app_error).toString());

		} else if (ResponseConstant.EXCEPTION.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_exception).toString());

		} else if (ResponseConstant.DUPLICATED.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_duplicated).toString());

		} else if (ResponseConstant.LOCKUSER.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_userlock).toString());

		} else if (ResponseConstant.LOCKDEVICE.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_devicelock).toString());

		} else if (ResponseConstant.TIMEOUT.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_timeout).toString());

		} else if (ErrorConstants.JSON_ERROR.equals(result)) {

			showToast(context.getResources().getString(R.string.error_request_jsonerror).toString());

		} else {

			showToast(context.getResources().getString(R.string.error_app_unknow).toString());

		}
	}
	
	public void showToast(String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}

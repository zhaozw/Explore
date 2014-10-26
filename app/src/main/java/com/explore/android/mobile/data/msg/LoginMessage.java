package com.explore.android.mobile.data.msg;

import com.explore.android.R;

public class LoginMessage {

	public static final String LOGIN_NOUSER = "NOUSER";// 登录常量：用户不存在

	public static final String LOGIN_NODEVICE = "NODEVICE";// 登录常量：设备未注册

	public static final String LOGIN_NOBINDING = "NOBINDING";// 登录常量：设备未绑定到用户

	public static final String LOGIN_NOBINDINGIMEI = "NOBINDINGIMEI";// 错误的IMEI

	public static final String LOGIN_NOBINDINGIMSI = "NOBINDINGIMSI"; // 错误的IMSI

	public static final String LOGIN_LOCKDEVICE = "LOCKDEVICE"; // 设备被锁定

	public static final String LOGIN_EXCEPTION = "EXCEPTION"; // 服务器异常

	public static final String LOGIN_PWDERROR = "PASSWORDERROR"; // 密码错误

	public static final String LOGIN_LOCKUSER = "LOCKUSER"; // 用户被锁定

	public static final String REGISTER_DUPLICATED = "DUPLICATED"; // 设备已经被注册

	public static int trans(String response) {

		if (LOGIN_NOUSER.equals(response)) {
			return R.string.error_register_nouser;

		} else if (LOGIN_NODEVICE.equals(response)) {
			return R.string.error_login_nodevice;

		} else if (LOGIN_NOBINDING.equals(response)) {
			return R.string.error_login_nobinding;

		} else if (LOGIN_NOBINDINGIMEI.equals(response)) {
			return R.string.error_login_nobindingimei;

		} else if (LOGIN_NOBINDINGIMSI.equals(response)) {
			return R.string.error_login_nobindingimsi;

		} else if (LOGIN_LOCKDEVICE.equals(response)) {
			return R.string.error_request_devicelock;

		} else if (LOGIN_EXCEPTION.equals(response)) {
			return R.string.error_request_exception;

		} else if (LOGIN_PWDERROR.equals(response)) {
			return R.string.error_login_pwderror;

		} else if (LOGIN_LOCKUSER.equals(response)) {
			return R.string.error_request_userlock;

		} else if (REGISTER_DUPLICATED.equals(response)) {
			return R.string.error_request_duplicated;

		} else {
			return R.string.error_default;
		}
	}
}

package com.explore.android.mobile.model;

public class DeviceInfo {

	private String imsi;
	private String imei;
	private String imsiL4;
	private String imeiL4;
	private String mac;

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImsiL4() {
		return imsiL4;
	}

	public void setImsiL4(String imsiL4) {
		this.imsiL4 = imsiL4;
	}

	public String getImeiL4() {
		return imeiL4;
	}

	public void setImeiL4(String imeiL4) {
		this.imeiL4 = imeiL4;
	}

}

package com.hf.info;

public class HFLocalRouterInfo {
	private String ssid;
	private String pswd;
	public HFLocalRouterInfo(String ssid,String pswd){
		this.ssid = ssid;
		this.pswd = pswd;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}

package com.hf.itf;

public interface IHFSMTLKHelper {
	public static int SMRTLKV2 = 1;
	public static int SMRTLKV3 = 2;
	public static int SMRTLKv4 = 3;
	public void startSmartlinkV30(String pswd);
	public void stopSmartlinkV30();
	
	public void startSmartlinkV40(String ssid,String pswd);
	public void stopSmartlinkV40();
	
}

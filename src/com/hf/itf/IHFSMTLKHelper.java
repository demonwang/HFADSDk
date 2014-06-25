package com.hf.itf;

import com.hf.util.HFModuleException;

public interface IHFSMTLKHelper {
	public static int SMRTLKV2 = 1;
	public static int SMRTLKV3 = 2;
	public static int SMRTLKv4 = 3;
	/**
	 * run one time smartlink.
	 * @param pswd
	 * @throws HFModuleException 
	 */
	public void startSmartlinkV30(String pswd) throws HFModuleException;
	public void stopSmartlinkV30();
	
	public void startSmartlinkV40(String ssid,String pswd);
	public void stopSmartlinkV40();
	
}

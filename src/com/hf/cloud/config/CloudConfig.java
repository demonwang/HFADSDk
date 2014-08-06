package com.hf.cloud.config;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class CloudConfig implements Cloneable{
	
	public String cloudServiceUrl = "http://node-cn.iotworkshop.com/usvc/";
	public static String cloudUserName;
	public static String cloudUserNickName;
	public static String cloudUserPhone;
	public static String cloudUserEmail;
	public static String cloudPassword;
	public static String cloudSharePassword;
	public static int 	 localUDPPort = 38899;
	public static InetAddress broudcastIp ;
	public static int 	 macTMsgPacketSize = 2048;
	public static int 	 pulseInterval = 5; //second
	public static int 	 defautTimeout = 5000;
	
	public Map<String, String> header = new HashMap<String, String>();

	@Override
	public CloudConfig clone() {
		// TODO Auto-generated method stub
		try {
			return (CloudConfig)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			
			return null;
		}
	}
}

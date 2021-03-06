package com.hf.data;

import java.net.InetAddress;

import com.hf.itf.IHFSMTLKHelper;
import com.hf.smartlink.HFSMTLKHelper;

import android.content.Context;

/**
 * 
 * @author demon
 *
 */
public class HFConfigration {
	public static String accessKey = "8a21049f466105fc0146d199e35100f5";
	public static int smtlkVer = IHFSMTLKHelper.SMRTLKV3; // 1:2.0;	2:3.0;	3:3.0
	public static String cloudHomeServiceUrl = "http://www.iotworkshop.com/";
	public static String cloudServiceUrl = "http://node-cn.iotworkshop.com/usvc/";
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
	
	/**
	 * Initialized when the program is running
	 */
	public static Context appContex; //

	public static String defLocalKey = "1234567890abcdef";
	public static int cloudPort = 47172;
	public static String cloudDomain = "node-cn.iotworkshop.com";
	public static int smartlinkPort = 39999;
}

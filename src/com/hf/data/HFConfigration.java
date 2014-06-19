package com.hf.data;

import java.net.InetAddress;

import android.content.Context;

/**
 * 
 * @author demon
 *
 */
public class HFConfigration {
	public static String accessKey = "8a21049f466068f90146607eaaa1022a";
	public static String smtlkVer; // 1:2.0;	2:3.0;	3:3.0
	public static String cloudHomeServiceUrl;
	public static String cloudServiceUrl = "http://115.29.164.59/usvc/";
	public static String cloudUserName;
	public static String cloudUserNickName;
	public static String cloudUserPhone;
	public static String cloudUserEmail;
	public static String cloudPassword;
	public static String cloudSharePassword;
	public static int 	 localUDPPort;
	public static InetAddress broudcastIp;
	public static int 	 macTMsgPacketSize = 2048;
	public static int 	 pulseInterval = 30; //second
	public static int 	 defautTimeout = 5000;
	public static Context appContex; //
}

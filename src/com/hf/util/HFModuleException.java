package com.hf.util;

public class HFModuleException extends Exception{
	private int errorCode = 0;
	
	public static final int ERR_SEND_CMD = -500;
	public static final int ERR_RECV_CMD = -501;
	public static final int ERR_HTTP_SEND_CMD = -502;
	public static final int ERR_HTTP_RECV_CMD = -503;
	public static final int ERR_JSON_DECODE = -505;
	public static final int ERR_AES = -504;
	public static final int ERR_COULD_NOT_ALIVE = -506;

	public static final int ERR_BROADCAST_GET = -507;

	public static final int ERR_USER_OFFLINE = -506;

	public static final int ERR_GET_USER = -507;

	public static final int ERR_SET_USER = -508;

	public static final int ERR_CHANGE_PSWD = -508;

	public static final int ERR_GET_PSWD = -509;

	public static final int ERR_SET_KEYVALUE = -510;

	public static final int ERR_GET_KEYVALUE = -511;

	public static final int ERR_DELETE_KEYVALUE = -512;

	public static final int ERR_SET_MODULE = -513;

	public static final int ERR_GET_REMOTE_MODULEINFO = -514;

	public static final int ERR_DELETE_MODULE = -515;

	public HFModuleException(int errorCode,String e){
		super(e);
		this.errorCode = errorCode;
	}
	
	public int getErrcode(){
		return this.errorCode;
	}
}

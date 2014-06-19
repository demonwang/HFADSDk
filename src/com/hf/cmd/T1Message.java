package com.hf.cmd;

import com.hf.util.AES;
import com.hf.util.HFModuleException;

public abstract class T1Message {

	private byte T1Cmd;
	private byte flag;
	private String mac;
	private byte proVer;
	private byte rsv;
	private int sn;
	private int factoryId;
	private int deviceType;
	
	private String key = AES.DEF_KEY;
		
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public byte getT1Cmd() {
		return T1Cmd;
	}
	public void setT1Cmd(byte t1Cmd) {
		T1Cmd = t1Cmd;
	}
	public byte getFlag() {
		return flag;
	}
	public void setFlag(byte flag) {
		this.flag = flag;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public byte getProVer() {
		return proVer;
	}
	public void setProVer(byte proVer) {
		this.proVer = proVer;
	}
	public byte getRsv() {
		return rsv;
	}
	public void setRsv(byte rsv) {
		this.rsv = rsv;
	}
	
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public int getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public abstract byte[] pack() throws HFModuleException;
	public abstract T1Message unpack(byte[] t1);
}

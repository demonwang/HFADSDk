package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.util.ByteTool;

public class T2Massage {
	byte flag1;
	byte flag2;
	byte[] length;
	byte cmd;
	byte[] data;
	
	public byte getFlag1() {
		return flag1;
	}
	public void setFlag1(byte flag1) {
		this.flag1 = flag1;
	}
	public byte getFlag2() {
		return flag2;
	}
	public void setFlag2(byte flag2) {
		this.flag2 = flag2;
	}
	public byte[] getLength() {
		return length;
	}
	public void setLength(byte[] length) {
		this.length = length;
	}
	public byte getCmd() {
		return cmd;
	}
	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(data.length+5);
		bf.put(flag1);
		bf.put(flag2);
		bf.put(ByteTool.Int2Byte(5+data.length));
		bf.put(cmd);
		bf.put(data);
		return bf.array();
	}
	public void unpack(byte[] data){
		this.setFlag1(data[0]);
		this.setFlag2(data[1]);
		this.setLength(ByteTool.copyOfRange(data, 2, 4));
		this.setCmd(data[4]);
		this.setData(ByteTool.copyOfRange(data, 5, data.length));
	} 
}

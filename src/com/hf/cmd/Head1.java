package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.util.ByteTool;

public class Head1 {
	private byte cmd;
	private Flag flag;
	private String mac;

	public Head1 unpack(byte[] head1) {
		this.setCmd(head1[0]);
		this.setFlag(new Flag().unpack(head1[1]));
		this.setMac(ByteTool.bytes2HexString(ByteTool.copyOfRange(head1, 2,
				8)));
		return this;
	}

	public byte getCmd() {
		return cmd;
	}

	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}

	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public byte[] pack() {
		ByteBuffer bf = ByteBuffer.allocateDirect(8);
		bf.put(cmd);
		bf.put(flag.pack());
		bf.put(ByteTool.hexStringToBytes(mac));
		return bf.array();
	}
}


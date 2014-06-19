package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.util.AES;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

public class T1CtrlMassage extends T1Message{
	private byte[] t2;
	
	public T1CtrlMassage(){
		this.setT1Cmd((byte) 0x11);
		this.setFlag((byte) 0x49);
	}
	
	public byte[] getT2() {
		return t2;
	}

	public void setT2(byte[] t2) {
		this.t2 = t2;
	}

	@Override
	public  byte[] pack() throws HFModuleException {
		// TODO Auto-generated method stub
		ByteBuffer bf = ByteBuffer.allocate(16+t2.length);
		bf.put(0, getT1Cmd());
		bf.put(1, getFlag());
		bf.put(ByteTool.hexStringToBytes(getMac()));
		bf.put(8, getProVer());
		bf.put(9, getRsv());
		bf.put(ByteTool.Int2Byte(getSn()));
		bf.put(ByteTool.Int2Byte(getFactoryId()));
		bf.put(ByteTool.Int2Byte(getDeviceType()));
		bf.put(t2);
		byte[] data = bf.array();
		byte[] jiami = ByteTool.copyOfRange(data, 8, data.length);
		byte[] jiamihou = AES.encrypt(jiami, getKey());
		ByteBuffer bf1 = ByteBuffer.allocate(8+jiamihou.length);
		bf1.put(ByteTool.copyOfRange(data, 0, 8));
		bf1.put(jiamihou);
		return bf1.array();
	}

	@Override
	public T1Message unpack(byte[] t1) {
		// TODO Auto-generated method stub
		this.setT1Cmd(t1[0]);
		this.setFlag(t1[1]);
		this.setMac(ByteTool.bytes2HexString(ByteTool.copyOfRange(t1, 2, 8)));
		this.setProVer(t1[8]);
		this.setRsv(t1[9]);
		this.setSn(ByteTool.Byte2Int(ByteTool.copyOfRange(t1, 10, 12)));
		this.setFactoryId(ByteTool.Byte2Int(ByteTool.copyOfRange(t1, 12, 14)));
		this.setDeviceType(ByteTool.Byte2Int(ByteTool.copyOfRange(t1, 14, 16)));
		this.setT2(ByteTool.copyOfRange(t1, 16, t1.length));
		return this;
	}
}

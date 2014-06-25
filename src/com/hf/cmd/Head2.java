package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.lib.util.AES;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

public class Head2 {
	private byte ver;
	private byte rsv;
	private byte[] sn;
	private byte[] fid;
	private byte[] dtype;
	private byte[] t2;

	public Head2 unpack(byte[] head2 ,String key) throws HFModuleException {
		
		try {
			byte[]	data = AES.decrypt(head2, key.getBytes());
		
		setVer(data[0]);
		setRsv(head2[1]);
		setSn(ByteTool.copyOfRange(data, 2, 4));
		setFid(ByteTool.copyOfRange(data, 4, 6));
		setDtype(ByteTool.copyOfRange(data, 6, 8));
		setT2(ByteTool.copyOfRange(data, 8, head2.length));
		return this;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
		}
	}

	public byte getVer() {
		return ver;
	}

	public void setVer(byte ver) {
		this.ver = ver;
	}

	public byte getRsv() {
		return rsv;
	}

	public void setRsv(byte rsv) {
		this.rsv = rsv;
	}

	public byte[] getSn() {
		return sn;
	}

	public void setSn(byte[] sn) {
		this.sn = sn;
	}

	public byte[] getFid() {
		return fid;
	}

	public void setFid(byte[] fid) {
		this.fid = fid;
	}

	public byte[] getDtype() {
		return dtype;
	}

	public void setDtype(byte[] dtype) {
		this.dtype = dtype;
	}

	public byte[] getT2() {
		return t2;
	}

	public void setT2(byte[] t2) {
		this.t2 = t2;
	}

	public byte[] pack(String key) throws HFModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(8 + t2.length);
		bf.put(ver);
		bf.put(rsv);
		bf.put(sn);
		bf.put(fid);
		bf.put(dtype);
		bf.put(t2);
		return AES.encrypt(bf.array(), key.getBytes());
		}catch(Exception e){
			throw new HFModuleException(HFModuleException.ERR_AES, e.getMessage());
		}
	}
}

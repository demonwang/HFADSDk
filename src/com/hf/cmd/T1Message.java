package com.hf.cmd;

import java.nio.ByteBuffer;

import com.hf.lib.util.AES;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

public class T1Message {

	private String key = AES.DEFAULT_KEY_128;
	private Head1 h1;
	private Head2 h2;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Head1 getH1() {
		return h1;
	}

	public void setH1(Head1 h1) {
		this.h1 = h1;
	}

	public Head2 getH2() {
		return h2;
	}

	public void setH2(Head2 h2) {
		this.h2 = h2;
	}

	public byte[] pack() throws HFModuleException {
		byte[] head1 = h1.pack();
		byte[] head2 = h2.pack(getKey());
		ByteBuffer bf = ByteBuffer.allocate(head1.length + head2.length);
		bf.put(head1);
		bf.put(head2);
		return bf.array();
	}

	public T1Message unpack(byte[] t1) throws HFModuleException {
		if(t1.length<16){
			throw new HFModuleException(12, "unpack t1 msg err");
		}
		h1 = new Head1().unpack(ByteTool.copyOfRange(t1, 0, 8));
		h2 = new Head2().unpack(ByteTool.copyOfRange(t1, 8, t1.length),getKey());
		return this;
	}
}

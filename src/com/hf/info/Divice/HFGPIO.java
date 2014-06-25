package com.hf.info.Divice;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

import com.hf.cmd.T2Massage;
import com.hf.info.GPIO;
import com.hf.info.ModuleInfo;

public class HFGPIO extends ModuleInfo{	
	
	public byte[] setGPIO(int id,boolean stat){
		T2Massage t2 = new T2Massage();
		t2.setFlag1((byte) 0xFE);
		t2.setFlag2((byte) 0x01);
		t2.setCmd((byte) 0x01);
		GPIO g = new GPIO();
		g.setGpioid(id);
		g.setStat(stat);
		t2.setData(g.pack());
		return t2.pack();
	}
	
	public byte[] getGPIO(int id){
		T2Massage t2 = new T2Massage();
		t2.setFlag1((byte) 0xFD);
		t2.setFlag2((byte) 0x01);
		t2.setCmd((byte) 0x02);
		byte[] ids = new byte[1];
		ids[0] = (byte) id;
		t2.setData(ids);
		return t2.pack();
	}
	
	public byte[] getAllGPIO(int[]  ids){
		T2Massage t2 = new T2Massage();
		t2.setFlag1((byte) 0xFD);
		t2.setFlag2((byte) 0x01);
		t2.setCmd((byte) 0x02);
		byte[] mids = new byte[ids.length];
		for (int i = 0; i < ids.length; i++) {
			mids[i] = (byte) ids[i];
		}
		t2.setData(mids);
		return t2.pack();
	}
	public byte[] setAllGPIO( HashMap<Integer, Boolean> gs){
		T2Massage t2 = new T2Massage();
		t2.setFlag1((byte) 0xFD);
		t2.setFlag2((byte) 0x01);
		t2.setCmd((byte) 0x02);
		ByteBuffer bf = ByteBuffer.allocate(gs.size()*4);
		Iterator<Integer> it = gs.keySet().iterator();
		while(it.hasNext()){
			int key  = it.next();
			boolean value = gs.get(key);
			GPIO g = new GPIO();
			g.setGpioid(key);
			g.setStat(value);
			bf.put(g.pack());
		}
		t2.setData(bf.array());
		return t2.pack();
	}
}

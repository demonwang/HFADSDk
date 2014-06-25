package com.hf.info.Divice;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.hf.cmd.T2Massage;
import com.hf.info.GPIO;
import com.hf.info.ModuleInfo;
import com.hf.util.ByteTool;

public class HFGPIO extends ModuleInfo{	
	ArrayList<GPIO> gpios = new ArrayList<GPIO>();
	
	public byte[] setGPIOCMD(int id,boolean stat){
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
	
	public byte[] getGPIOCMD(int id){
		T2Massage t2 = new T2Massage();
		t2.setFlag1((byte) 0xFD);
		t2.setFlag2((byte) 0x01);
		t2.setCmd((byte) 0x02);
		byte[] ids = new byte[1];
		ids[0] = (byte) id;
		t2.setData(ids);
		return t2.pack();
	}
	
	public byte[] getAllGPIOCMD(int[]  ids){
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
	public byte[] setAllGPIOCMD( HashMap<Integer, Boolean> gs){
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
	
	public ArrayList<GPIO> unpackGPIODataFromT2(T2Massage t2){
		ArrayList<GPIO> gpios = new ArrayList<GPIO>();
		byte[] data = t2.getData();
		for (int i = 0; i < data.length/4; i++) {
			byte[] gpiodata = ByteTool.copyOfRange(data, i*4, (i+1)*4);
			GPIO g = new GPIO();
			g.setGpioid(gpiodata[0]>>4);
			if((gpiodata[1]&0x0f)==0x02){
				g.setStat(true);
			}else{
				g.setStat(false);
			}
			gpios.add(g);
		}
		return gpios;
	} 
}

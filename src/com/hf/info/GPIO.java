package com.hf.info;

import java.util.ArrayList;

import com.hf.info.Divice.HFGPIO;
import com.hf.util.ByteTool;

public class GPIO{
	int gpioid;
	boolean stat;
	byte[] gpio = ByteTool.hexStringToBytes("F00FFFFF");
	
	
	public int getGpioid() {
		return gpioid;
	}

	public void setGpioid(int gpioid) {
		this.gpioid = gpioid;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean stat) {
		this.stat = stat;
	}

	public byte[] pack(){
		gpio[0] = (byte) (gpioid<<4);
		if(isStat())
			gpio[1] = 0x02;
		else
			gpio[1] = 0x01;
		return gpio;
	}
	public ArrayList<GPIO> unpack(byte[] data){
		ArrayList<GPIO> gpios = new ArrayList<GPIO>();
		for (int i = 0; i < data.length/4; i++) {
			GPIO g = new GPIO();
			g.setGpioid(data[i*4]>>4);
			if(data[1]==0x01){
				g.setStat(false);
			}
			else if(data[1]==0x02){
				g.setStat(true);
			}
			gpios.add(g);
		}
		return gpios;
	}
}

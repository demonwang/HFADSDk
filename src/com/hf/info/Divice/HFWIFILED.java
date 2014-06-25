package com.hf.info.Divice;

import java.nio.ByteBuffer;

import com.hf.util.ByteTool;

/**
 * 

调节色温

发送 ：FE 06 0002 80 XX        XX:00~64    0%~100%

返回 ：FE 06 0002 80 XX        

调节亮度

发送 ：FE 06 0002 81 XX        XX:00~64    0%~100%

返回 ：FE 06 0002 81 XX

开关

发送 ：FE 06 0002 82 00 （关）    XX:00 或 01

发送 ：FE 06 0002 82 01 （开）

返回 ：FE 06 0002 82 XX

读状态

发送 ：FD 06 0001 83

返回 ：FD 06 0003 83 XX XX XX （开关，亮度，色温）
 * @author Administrator
 *
 */
public class HFWIFILED {
	public byte[] setTempCmd(int temp){
		ByteBuffer bf = ByteBuffer.allocate(6);
		bf.put(ByteTool.hexStringToBytes("FE06000280"));
		bf.put((byte) temp);
		return bf.array();
	}
	public byte[] setLightCmd(int light){
		ByteBuffer bf = ByteBuffer.allocate(6);
		bf.put(ByteTool.hexStringToBytes("FE06000281"));
		bf.put((byte) light);
		return bf.array();
	}
	
	public byte[] setStateCmd(boolean state){
		ByteBuffer bf = ByteBuffer.allocate(6);
		bf.put(ByteTool.hexStringToBytes("FE06000282"));
		if(state){
			bf.put((byte) 0x01);
		}else{
			bf.put((byte) 0);
		}
		
		return bf.array();
	}
	
	public byte[] getStateCmd(){
		ByteBuffer bf = ByteBuffer.allocate(6);
		bf.put(ByteTool.hexStringToBytes("FE06000283"));
		return bf.array();
	}
}

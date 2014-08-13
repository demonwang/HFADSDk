package com.hf.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Random;

import com.hf.ManagerFactory;
import com.hf.cmd.Flag;
import com.hf.cmd.Head1;
import com.hf.cmd.Head2;
import com.hf.cmd.T1Message;
import com.hf.data.HFConfigration;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFModuleLocalManager;
import com.hf.itf.IHFSMTLKHelper;
import com.hf.lib.util.AES;
import com.hf.smartlink.HFSMTLKHelper;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;
import com.hf.util.UdpProxy;
/**
 * 
 * @author Administrator
 *
 */
public class HFModuleLocalManager implements IHFModuleLocalManager {

	private static IHFSMTLKHelper smartlinkHelper = null;
	private DatagramSocket socket = null;

	HFModuleLocalManager(DatagramSocket socket) {
		this.socket = socket;
	}

	@Override
	public void sendLocalBeatNow() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				byte[] data = "~@@Hello,Thing!**#".getBytes();
				ByteBuffer bf = ByteBuffer.allocate(3+data.length);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put(data);
				DatagramPacket beat = new DatagramPacket(bf.array(), bf.array().length,HFConfigration.broudcastIp,HFConfigration.localUDPPort);
				try {
					socket.send(beat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}


	@Override
	public IHFSMTLKHelper getHFSMTLKHelper() {
		// TODO Auto-generated method stub
		if(smartlinkHelper == null){
			smartlinkHelper = new HFSMTLKHelper(null);
		}
		return smartlinkHelper;
	}

	@Override
	public ModuleInfo setNewModuleLocalInfo(ModuleInfo  mi) throws HFModuleException {
		// TODO Auto-generated method stub
		if(mi == null){
			throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "your module is null");
		}
		//set local key
		try{
			T1Message t1 = new T1Message();
			Head1 h1 = new Head1();
			Head2 h2 = new Head2();
			Flag flag = new Flag();
			flag.unpack((byte) 0x41);
			h1.setCmd((byte) 0x02);
			h1.setFlag(flag);
			h1.setMac(mi.getMac());
			t1.setH1(h1);
			h2.setVer((byte) 0x01);
			h2.setRsv((byte) 0);
			h2.setSn(ByteTool.Int2Byte(new Random(2048).nextInt()));
			h2.setFid(ByteTool.Int2Byte(mi.getFactoryId()));
			h2.setDtype(ByteTool.Int2Byte(mi.getType()));
			ByteBuffer bf = ByteBuffer.allocate(17);
			bf.put((byte) 16);
			bf.put(HFConfigration.defLocalKey.getBytes());
			h2.setT2(bf.array());
			t1.setH2(h2);
			t1.setKey(AES.DEFAULT_KEY_128);
			byte[] data = t1.pack();
			byte[]  rspdata = UdpProxy.reqByUdp(mi.getLocalIp(), data);
			t1.unpack(rspdata);
			if(t1.getH1().getCmd() != 0x02){
				throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "set moduel pswd err");
			}
			
			if(t1.getH1().getFlag().pack() != 0x43){
				throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "set moduel pswd err");
			}
			
			//set serveradd	
			
			h1.setCmd((byte) 0x0A);
			flag.unpack((byte) 0x41);
			h1.setFlag(flag);
			h1.setMac(mi.getMac());
			h2.setVer((byte) 0x01);
			h2.setRsv((byte) 0);
			h2.setSn(ByteTool.Int2Byte(new Random(2048).nextInt()));
			h2.setFid(ByteTool.Int2Byte(mi.getFactoryId()));
			h2.setDtype(ByteTool.Int2Byte(mi.getType()));
			ByteBuffer bf1 = ByteBuffer.allocate(12+HFConfigration.cloudDomain.getBytes().length);
			bf1.put(ByteTool.Int2Byte(HFConfigration.cloudPort));
			byte[] rsv = new byte[8];
			bf1.put(rsv);
			bf1.put(ByteTool.Int2Byte(HFConfigration.cloudDomain.getBytes().length));
			bf1.put(HFConfigration.cloudDomain.getBytes());
			h2.setT2(bf1.array());
			t1.setH1(h1);
			t1.setH2(h2);
			t1.setKey(HFConfigration.defLocalKey);
			byte[] data1 = t1.pack();
			byte[]  rspdata1 = UdpProxy.reqByUdp(mi.getLocalIp(), data1);
			t1.unpack(rspdata1);
			if(t1.getH1().getCmd() != 0x0A){
				throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "set moduel server domain err");
			}
			if(t1.getH1().getFlag().pack() != 0x43){
				throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "set moduel server domain err");
			}
			mi.setLocalKey(HFConfigration.defLocalKey);
			return mi;
		}catch(Exception e){
			throw new HFModuleException(HFModuleException.ERR_SET_MODULE_LOCCAL_INFO, "set moduel  err");
		}		
	}

}

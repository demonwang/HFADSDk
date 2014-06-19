package com.hf.helper;

import java.util.Random;

import android.util.Log;

import com.hf.cmd.T1CtrlMassage;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFSendAsyncCmdHelper;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

public class HFSendAsyncCmdHelper implements IHFSendAsyncCmdHelper{

	@Override
	public synchronized void sendLocalCmdAsync(final ModuleInfo mi,final byte[] t2,AsyncCmdEvent et) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				T1CtrlMassage msg = new T1CtrlMassage();
				msg.setMac(mi.getMac());
				msg.setFactoryId(mi.getFactoryId());
				msg.setDeviceType(mi.getType());
				msg.setSn(new Random().nextInt(65535));
				msg.setProVer((byte) 1);
				msg.setKey(mi.getLocalKey());
				msg.setT2(t2);
				try {
					byte[] cmd = msg.pack();
					Log.i("UNDone", ByteTool.Byte2StringWithSpace(cmd));
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	@Override
	public void sendCmdAsync(ModuleInfo mi,final byte[] t2, AsyncCmdEvent evt) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("UNDone", ByteTool.Byte2StringWithSpace(t2));
			}
		}).start();
	}
	
	
}

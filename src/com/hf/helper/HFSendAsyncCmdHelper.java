package com.hf.helper;

import com.hf.info.ModuleInfo;
import com.hf.itf.IHFSendAsyncCmdHelper;
import com.hf.util.HFModuleException;

public class HFSendAsyncCmdHelper implements IHFSendAsyncCmdHelper{

	@Override
	public synchronized void sendLocalCmdAsync(final ModuleInfo mi,final byte[] t2,final AsyncCmdEvent et) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					byte[] data = HFSendCmdHelper.getSendSyncCmdHelper().sendLocalMsg(mi, t2);
					et.onRecv(mi, data);
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					et.onRecv(mi, null);
				}
			}
		}).start();
	}

	@Override
	public void sendCmdAutoAsync(final ModuleInfo mi,final byte[] t2, final AsyncCmdEvent evt) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					byte[] data = HFSendCmdHelper.getSendSyncCmdHelper().sendAuto(mi, t2);
					evt.onRecv(mi, data);
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					evt.onRecv(mi, null);
				}
			}
		}).start();
	}
}

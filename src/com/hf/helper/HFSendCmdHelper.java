package com.hf.helper;

import com.hf.itf.IHFSendAsyncCmdHelper;
import com.hf.itf.IHFSendCmdHelper;
import com.hf.itf.IHFSendSyncCmdHelper;

public class HFSendCmdHelper implements IHFSendCmdHelper{
	private IHFSendAsyncCmdHelper sendAsyncCmdHelper = null;
	private IHFSendSyncCmdHelper sndSyncCmdHelper = null;
	@Override
	public IHFSendAsyncCmdHelper getSendAsyncCmdHelper() {
		// TODO Auto-generated method stub
		if(sendAsyncCmdHelper == null){
			sendAsyncCmdHelper = new HFSendAsyncCmdHelper();
		}
		return sendAsyncCmdHelper;
	}
	
	@Override
	public IHFSendSyncCmdHelper getSendSyncCmdHelper() {
		// TODO Auto-generated method stub
		if(sndSyncCmdHelper == null){
			sndSyncCmdHelper = new HFSendSyncCmdHelper();
		}
		return sndSyncCmdHelper;
	}
}

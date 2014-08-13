package com.hf.helper;

import com.hf.data.HFConfigration;
import com.hf.itf.IHFSendAsyncCmdHelper;
import com.hf.itf.IHFSendSyncCmdHelper;

public class HFSendCmdHelper{
	private static IHFSendAsyncCmdHelper sendAsyncCmdHelper = null;
	private static IHFSendSyncCmdHelper sndSyncCmdHelper = null;

	public static IHFSendAsyncCmdHelper getSendAsyncCmdHelper() {
		// TODO Auto-generated method stub
		if(sendAsyncCmdHelper == null){
			sendAsyncCmdHelper = new HFSendAsyncCmdHelper();
		}
		return sendAsyncCmdHelper;
	}
	
	public static IHFSendSyncCmdHelper getSendSyncCmdHelper() {
		// TODO Auto-generated method stub
		if(sndSyncCmdHelper == null){
			sndSyncCmdHelper = new HFSendSyncCmdHelper(HFConfigration.appContex);
		}
		return sndSyncCmdHelper;
	}
}

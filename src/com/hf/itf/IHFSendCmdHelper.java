package com.hf.itf;

public interface IHFSendCmdHelper {
	public IHFSendAsyncCmdHelper getSendAsyncCmdHelper();
	public IHFSendSyncCmdHelper getSendSyncCmdHelper();
}

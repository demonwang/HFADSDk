package com.hf.itf;

import com.hf.info.ModuleInfo;

/**
 * if Async only Local?
 * @author Administrator
 *
 */
public interface IHFSendAsyncCmdHelper {
	/**
	 * 
	 * @param mi
	 * @param t2
	 * @param evt
	 */
	public void sendCmdAutoAsync(ModuleInfo mi,byte[] t2,AsyncCmdEvent evt);
	/**
	 * 
	 * @param mi
	 * @param t2
	 * @param evt
	 */
	public void sendLocalCmdAsync(ModuleInfo mi,byte[] t2,AsyncCmdEvent evt);
	/**
	 * 
	 * @author Administrator
	 *
	 */
	interface  AsyncCmdEvent {
		/**
		 * 
		 * @param mi
		 * @param t2 if is null  send err
		 */
		void onRecv(ModuleInfo mi,byte[] t2);
	}
}

package com.hf.itf;

import com.hf.info.ModuleInfo;
import com.hf.util.HFModuleException;

public interface IHFSendSyncCmdHelper {

	/**
	 * 
	 * @param mi
	 * @param msg
	 * @throws HFModuleException 
	 */
	public byte[] sendLocalMsg(ModuleInfo mi,byte[] msg) throws HFModuleException;
	/**
	 * 
	 * @param mac
	 * @param msg only t2
	 * @throws HFModuleException 
	 */
	public byte[] sendServerMsg(ModuleInfo mi,byte[] msg) throws HFModuleException;
	/**
	 * 
	 * @param mi
	 * @param msg only T2msg
	 * @throws HFModuleException 
	 */
	public byte[] sendAuto(ModuleInfo mi,byte[] msg) throws HFModuleException;
}

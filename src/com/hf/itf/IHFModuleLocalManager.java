package com.hf.itf;

import com.hf.info.ModuleInfo;
import com.hf.util.HFModuleException;

public interface IHFModuleLocalManager {
	/**
	 * you can use this after smartlink
	 */
	public void sendLocalBeatNow();
	/**
	 * set module localKey & server Domian  serverIP
	 * @param mac
	 * @return
	 * @throws HFModuleException
	 */
	public ModuleInfo setNewModuleLocalInfo(ModuleInfo mac) throws HFModuleException;
	/**
	 * get smartlink IHFSMTLKHelper 
	 * @return
	 */
	public IHFSMTLKHelper getHFSMTLKHelper();
}

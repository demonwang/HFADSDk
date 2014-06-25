package com.hf.itf;

import com.hf.info.ModuleInfo;
import com.hf.util.HFModuleException;

public interface IHFModuleLocalManager {
	public void sendLocalBeatNow();
	public ModuleInfo setNewModuleLocalInfo(ModuleInfo mac) throws HFModuleException;
	public IHFSMTLKHelper getHFSMTLKHelper();
}

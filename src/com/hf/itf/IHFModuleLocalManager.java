package com.hf.itf;

import com.hf.helper.HFLocalSaveHelper;
import com.hf.info.ModuleInfo;

public interface IHFModuleLocalManager {
	public void sendLocalBeatNow();
	public void addNewModule(ModuleInfo mi);
	public IHFSMTLKHelper getHFSMTLKHelper();
}

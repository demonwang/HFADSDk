package com.hf;

import android.util.Log;

import com.hf.itf.IHFModuleLocalManager;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.manager.HFModuleLocalManager;
import com.hf.manager.HFModuleManager;
import com.hf.manager.HISFManager;

public class ManagerFactory {
	private static IHFModuleManager manager = null;
	private static IHFSFManager sfmanger = null;
	private static IHFModuleLocalManager moduellocalmanager = null;
	
	
	public static IHFModuleManager getManager(){
		if(manager == null){
			manager = new HFModuleManager();
		}
		return manager;
	}
	public static IHFSFManager getFSFManager(){
		if(sfmanger == null){
			sfmanger = new HISFManager();
		}
		return sfmanger;
	}
	public IHFModuleLocalManager getHFModuleLocalManager() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getHFModuleLocalManager");
		if(moduellocalmanager == null){
			moduellocalmanager = new HFModuleLocalManager();
		}
		return moduellocalmanager;
	}
}

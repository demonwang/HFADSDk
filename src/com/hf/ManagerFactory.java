package com.hf;

import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.manager.HFModuleManager;
import com.hf.manager.HISFManager;
import com.hf.smartlink.HFSMTLKHelper;
/**
 * 
 * @author Administrator
 *
 */
public class ManagerFactory {
	
	private static IHFModuleManager manager = null;
	private static IHFSFManager sfmanger = null;
	private HFSMTLKHelper smartLinker;
	
	private ManagerFactory() {
		manager = new HFModuleManager();
		sfmanger = new HISFManager();
		smartLinker = new HFSMTLKHelper();
	}
	
	private static class ManagerFactoryInner {
		private static ManagerFactory managerFactory = new ManagerFactory();
	}
	
	public static ManagerFactory getInstance() {
		return ManagerFactoryInner.managerFactory;
	}
	
	public IHFModuleManager getModuleManager(){
		return manager;
	}
	
	public IHFSFManager getSFManager(){
		return sfmanger;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getManager(Class<T> t) {
		
		if (t.getName().equals(HFSMTLKHelper.class.getName())) {
			return (T)getInstance().smartLinker;
		}
		
		throw new IllegalArgumentException("Not supported Class " + t.getName());
	}
}

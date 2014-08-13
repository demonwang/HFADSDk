package com.hf;

import android.content.Context;

import com.hf.cloud.CloudService;
import com.hf.cloud.config.CloudConfig;
import com.hf.data.HFConfigration;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.itf.IHFSMTLKHelper;
import com.hf.manager.HFModuleManager;
import com.hf.manager.HISFManager;
import com.hf.smartlink.HFSMTLKHelper;

/**
 * 
 * @author Administrator
 *
 */
public class ManagerFactory {
	
	private IHFSFManager sfmanger;
	private IHFSMTLKHelper smartLinker;
	private IHFModuleManager moduleManager;
	private CloudService cloudService;
	
	private Context context;
	
	private ManagerFactory() {
	}
	
	private void init() {
		moduleManager = new HFModuleManager(context);
		sfmanger = new HISFManager(context);
		smartLinker = new HFSMTLKHelper(context);
		
		cloudService = CloudService.getInstance();
		CloudConfig cloudConfig = new CloudConfig();
		cloudConfig.cloudServiceUrl = HFConfigration.cloudServiceUrl;
		cloudConfig.defautTimeout = HFConfigration.defautTimeout;
		cloudService.setConfig(cloudConfig);
	}
	
	private static class ManagerFactoryInner {
		private static ManagerFactory managerFactory = new ManagerFactory();
	}
	
	private static ManagerFactory getInstance(Context context) {
		ManagerFactory managerFactory = ManagerFactoryInner.managerFactory;
		if (managerFactory.context == null) {
			managerFactory.context = context.getApplicationContext();
			managerFactory.init();
		}
		return managerFactory;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getManager(Context context, Class<T> t) {
		
		ManagerFactory managerFactory = getInstance(context);
		String className = t.getName();
		
		if (className.equals(HFSMTLKHelper.class.getName()) || className.equals(IHFSMTLKHelper.class.getName())) {
			return (T)managerFactory.smartLinker;
		}else if (className.equals(HFModuleManager.class.getName()) || className.equals(IHFModuleManager.class.getName())) {
			return (T)managerFactory.moduleManager;
		}else if (className.equals(HISFManager.class.getName()) || className.equals(IHFSFManager.class.getName())) {
			return (T)managerFactory.sfmanger;
		}else if (className.equals(CloudService.class.getName())) {
			return (T)managerFactory.cloudService;
		}
		
		throw new IllegalArgumentException("Not supported Class " + t.getName());
	}
}

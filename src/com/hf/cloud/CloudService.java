package com.hf.cloud;

import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.manager.ISecurityManager;
import com.hf.cloud.manager.SecurityManager;

public class CloudService {
	
	private CloudConfig config;
	
	private CloudService() {
		super();
	}

	private static class CloudServiceInner {
		private static CloudService cloudService = new CloudService();
	}
	
	public static CloudService getInstance() {
		return CloudServiceInner.cloudService;
	}
	
	/**
	 * @param config the config to set
	 */
	public CloudService setConfig(CloudConfig config) {
		this.config = config;
		return this;
	}

	public CloudConfig getConfig() {
		return config.clone();
	}

	public ISecurityManager getSecurityManager() {
		return new SecurityManager(getConfig());
	}
}

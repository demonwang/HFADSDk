package com.hf.cloud.manager;

import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.module.ModuleDeleteRequest;
import com.hf.cloud.message.module.ModuleGetAllRequest;
import com.hf.cloud.message.module.ModuleGetAllResponse;
import com.hf.cloud.message.module.ModuleGetRequest;
import com.hf.cloud.message.module.ModuleSetRequest;
import com.hf.cloud.message.module.ModuleInfoResponse;

public class CloudModuleManager extends BaseCloudManager implements ICloudModuleManager {

	public CloudModuleManager(CloudConfig cloudConfig) {
		super(cloudConfig);
	}

	@Override
	public ModuleInfoResponse setModule(ModuleSetRequest request)
			throws CloudException {
		return postRequest(request, ModuleInfoResponse.class);
	}

	@Override
	public Response deleteModule(ModuleDeleteRequest request)
			throws CloudException {
		return postRequest(request, Response.class);
	}

	@Override
	public ModuleInfoResponse getModule(ModuleGetRequest request)
			throws CloudException {
		return postRequest(request, ModuleInfoResponse.class);
	}

	@Override
	public ModuleGetAllResponse getAllModule(ModuleGetAllRequest request)
			throws CloudException {
		return postRequest(request, ModuleGetAllResponse.class);
	}
}

package com.hf.cloud.manager;

import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.module.ModuleDeleteRequest;
import com.hf.cloud.message.module.ModuleGetAllRequest;
import com.hf.cloud.message.module.ModuleGetAllResponse;
import com.hf.cloud.message.module.ModuleGetRequest;
import com.hf.cloud.message.module.ModuleSetRequest;
import com.hf.cloud.message.module.ModuleInfoResponse;

public interface ICloudModuleManager {
	public ModuleInfoResponse setModule(ModuleSetRequest request) throws CloudException;
	public Response deleteModule(ModuleDeleteRequest request) throws CloudException;
	public ModuleInfoResponse getModule(ModuleGetRequest request) throws CloudException;
	public ModuleGetAllResponse getAllModule(ModuleGetAllRequest request) throws CloudException;
}

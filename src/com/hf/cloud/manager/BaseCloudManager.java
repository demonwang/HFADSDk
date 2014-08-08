package com.hf.cloud.manager;

import com.alibaba.fastjson.JSON;
import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.base.Request;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.security.UserIdResponse;
import com.hf.util.HFModuleException;
import com.hf.util.HttpProxy;

class BaseCloudManager {

	CloudConfig cloudConfig;

	public BaseCloudManager(CloudConfig cloudConfig) {
		super();
		this.cloudConfig = cloudConfig;
	}
	
	<T extends Response> T postRequest(Request request, Class<T> clazz) throws CloudException {

		String responseText;
		try {
			responseText = HttpProxy.reqByHttpPost(cloudConfig.cloudServiceUrl, cloudConfig.header, JSON.toJSONString(request));
		} catch (HFModuleException e) {
			e.printStackTrace();
			throw new CloudException(e.getErrorCode());
		}
		
		T response = JSON.parseObject(responseText, clazz);
		if (response.getReturnCode() != UserIdResponse.RETURN_CODE_OK) {
			throw new CloudException(response.getReturnCode(), response.getReason());
		}else {
			return response;	
		}
	}
}

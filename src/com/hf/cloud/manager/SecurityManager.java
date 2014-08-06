package com.hf.cloud.manager;

import com.alibaba.fastjson.JSON;
import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.base.Request;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.security.CaptchaImageRequest;
import com.hf.cloud.message.security.CaptchaResponse;
import com.hf.cloud.message.security.CaptchaTextRequest;
import com.hf.cloud.message.security.RetrievePasswordRequest;
import com.hf.cloud.message.security.UserGetRequest;
import com.hf.cloud.message.security.UserIdResponse;
import com.hf.cloud.message.security.UserLoginRequest;
import com.hf.cloud.message.security.UserLogoutRequest;
import com.hf.cloud.message.security.UserRegisterRequest;
import com.hf.cloud.message.security.UserResponse;
import com.hf.cloud.message.security.UserSetRequest;
import com.hf.util.HFModuleException;
import com.hf.util.HttpProxy;

public class SecurityManager implements ISecurityManager {
	
	private CloudConfig cloudConfig;

	public SecurityManager(CloudConfig cloudConfig) {
		super();
		this.cloudConfig = cloudConfig;
	}

	@Override
	public CaptchaResponse getCaptcha(CaptchaTextRequest textRequest)
			throws CloudException {
		return getCaptcha(JSON.toJSONString(textRequest));
	}

	@Override
	public CaptchaResponse getCaptcha(CaptchaImageRequest imageRequest)
			throws CloudException {
		return getCaptcha(JSON.toJSONString(imageRequest));
	}
	
	private CaptchaResponse getCaptcha(String json)
			throws CloudException {
		String responseText;
		try {
			responseText = HttpProxy.reqByHttpPost(cloudConfig.cloudServiceUrl, cloudConfig.header, json);
		} catch (HFModuleException e) {
			e.printStackTrace();
			throw new CloudException(e.getErrorCode());
		}
		
		CaptchaResponse captchaResponse = JSON.parseObject(responseText, CaptchaResponse.class);
		if (captchaResponse.getReturnCode() != CaptchaResponse.RETURN_CODE_OK) {
			throw new CloudException(captchaResponse.getReturnCode(), captchaResponse.getReason());
		}else {
			return captchaResponse;
		}
	}

	@Override
	public UserIdResponse registerUser(UserRegisterRequest request)
			throws CloudException {
		return postRequest(request, UserIdResponse.class);
	}

	@Override
	public UserIdResponse login(UserLoginRequest request) throws CloudException {
		return postRequest(request, UserIdResponse.class);
	}

	@Override
	public Response logout(UserLogoutRequest request) throws CloudException {
		// TODO Auto-generated method stub
		return postRequest(request, Response.class);
	}

	@Override
	public UserResponse getUser(UserGetRequest request) throws CloudException {
		return postRequest(request, UserResponse.class);
	}
	
	private <T extends Response> T postRequest(Request request, Class<T> clazz) throws CloudException {

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

	@Override
	public UserIdResponse setUser(UserSetRequest request) throws CloudException {
		return postRequest(request, UserIdResponse.class);
	}

	@Override
	public Response retrievePassword(RetrievePasswordRequest request)
			throws CloudException {
		return postRequest(request, Response.class);
	}
}

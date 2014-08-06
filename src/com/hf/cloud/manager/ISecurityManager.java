package com.hf.cloud.manager;

import com.hf.cloud.exception.CloudException;
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

public interface ISecurityManager {

	public CaptchaResponse getCaptcha(CaptchaTextRequest textRequest) throws CloudException;
	public CaptchaResponse getCaptcha(CaptchaImageRequest imageRequest) throws CloudException;
	public UserIdResponse registerUser(UserRegisterRequest request) throws CloudException;
	public UserIdResponse login(UserLoginRequest request) throws CloudException;
	public Response logout(UserLogoutRequest request) throws CloudException;
	public UserResponse getUser(UserGetRequest request) throws CloudException;
	public UserIdResponse setUser(UserSetRequest request) throws CloudException;
	public Response retrievePassword(RetrievePasswordRequest request) throws CloudException;
}

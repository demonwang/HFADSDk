package com.hf.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.hf.ManagerFactory;
import com.hf.cloud.CloudService;
import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.manager.ICloudModuleManager;
import com.hf.cloud.manager.ICloudSecurityManager;
import com.hf.cloud.message.module.ModuleGetAllRequest;
import com.hf.cloud.message.module.ModuleGetAllResponse;
import com.hf.cloud.message.module.ModuleGetRequest;
import com.hf.cloud.message.module.ModuleInfoResponse;
import com.hf.cloud.message.module.ModuleSetRequest;
import com.hf.cloud.message.module.payload.ModuleIdPayload;
import com.hf.cloud.message.security.CaptchaEmailRequest;
import com.hf.cloud.message.security.CaptchaImageRequest;
import com.hf.cloud.message.security.CaptchaResponse;
import com.hf.cloud.message.security.CaptchaSmsRequest;
import com.hf.cloud.message.security.CaptchaTextRequest;
import com.hf.cloud.message.security.RetrievePasswordRequest;
import com.hf.cloud.message.security.UserGetRequest;
import com.hf.cloud.message.security.UserIdResponse;
import com.hf.cloud.message.security.UserLoginRequest;
import com.hf.cloud.message.security.UserLogoutRequest;
import com.hf.cloud.message.security.UserRegisterRequest;
import com.hf.cloud.message.security.UserResponse;
import com.hf.cloud.message.security.UserSetRequest;
import com.hf.cloud.message.security.payload.RetrievePasswordPayload;
import com.hf.cloud.message.security.payload.UserLoginPayload;
import com.hf.cloud.message.security.payload.UserPayload;
import com.hf.cmd.T1Message;
import com.hf.cmd.T2Massage;
import com.hf.dao.IUserInfoDao;
import com.hf.dao.impl.UserInfoDao;
import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.helper.HFModuleHelper;
import com.hf.info.GPIO;
import com.hf.info.KeyValueInfo;
import com.hf.info.MessageReceiver;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.info.Divice.HFGPIO;
import com.hf.info.captcha.CaptchaImageInfo;
import com.hf.info.captcha.CaptchaInfo;
import com.hf.itf.IHFModuleEventListener;
import com.hf.itf.IHFModuleHelper;
import com.hf.itf.IHFModuleLocalManager;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.itf.IHFSFManager.SyncModuleEventListener;
import com.hf.lib.util.AES;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;
import com.hf.util.HttpProxy;

public class HFModuleManager implements IHFModuleManager {
	private DatagramSocket localBeatSocket;
	private boolean isAppRunning = false;
	public static String sid;
	private ArrayList<IHFModuleEventListener> eventListenerList = new ArrayList<IHFModuleEventListener>();
	private IHFModuleHelper hfModuleManager = null;

	private static IHFModuleLocalManager moduellocalmanager = null;
	
	private CloudService cloudService;
	private ICloudSecurityManager cloudSecurityManager;
	private ICloudModuleManager cloudModuleManager;
	
	private Context context;
	private IUserInfoDao userInfoDao;

	private void setSid(String sid) {
		this.sid = sid;
	}

	private String getsid() {
		return this.sid;
	}
	
	public HFModuleManager(Context context) {
		super();
		
		this.context = context;
		userInfoDao = new UserInfoDao(context);
		cloudService = CloudService.getInstance();
		
		CloudConfig cloudConfig = new CloudConfig();
		cloudConfig.cloudServiceUrl = HFConfigration.cloudServiceUrl;
		cloudConfig.defautTimeout = HFConfigration.defautTimeout;
		cloudService.setConfig(cloudConfig);
		cloudSecurityManager = cloudService.getCloudSecurityManager();
		cloudModuleManager = cloudService.getCloudModuleManager();
	}

	@Override
	public CaptchaImageInfo captchaText(MessageReceiver captchaReceiver)
			throws HFModuleException {
		
		CaptchaTextRequest request;
		if (captchaReceiver.getReceiverType() == MessageReceiver.RECEIVER_TYPE_EMAIL) {
			request = new CaptchaEmailRequest(captchaReceiver.getAddress());
		}else if (captchaReceiver.getReceiverType() == MessageReceiver.RECEIVER_TYPE_SMS) {
			request = new CaptchaSmsRequest(captchaReceiver.getAddress());
		}else {
			throw new IllegalArgumentException("Unsupported captcha receiver type: " + captchaReceiver.getReceiverType());
		}
		
		CaptchaImageInfo captchaInfo = null;
		CaptchaResponse captchaResponse;
		try {
			captchaResponse = cloudSecurityManager.getCaptcha(request);
			captchaInfo = captchaResponse.getPayload();
		} catch (CloudException e) {
			e.printStackTrace();
			throw new HFModuleException(e.getErrorCode(), e.getMessage());
		}
		
		return captchaInfo;
	}

	@Override
	public CaptchaImageInfo captchaImage() throws HFModuleException {
		
		CaptchaImageInfo captchaInfo = null;
		CaptchaResponse captchaResponse;
		try {
			captchaResponse = cloudSecurityManager.getCaptcha(new CaptchaImageRequest());
			captchaInfo = captchaResponse.getPayload();
		} catch (CloudException e) {
			e.printStackTrace();
			throw new HFModuleException(e.getErrorCode(), e.getMessage());
		}
		
		return captchaInfo;
	}


	@Override
	public synchronized String login(String username, String password) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "login");
		
		UserLoginPayload payload = new UserLoginPayload();
		payload.setAccessKey(HFConfigration.accessKey);
		payload.setUserName(username);
		payload.setPassword(password);
		payload.setMac(getMacAddress());
		payload.setClientName(android.os.Build.MANUFACTURER);
		
		UserLoginRequest request = new UserLoginRequest();
		request.setPayload(payload);
		
		try {
			UserIdResponse response = cloudSecurityManager.login(request);
			
			
			//[should remove these code later]
			this.setSid(response.getSessionId());
			HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
					.setUserName(HFConfigration.cloudUserName);
			HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
					.setPswd(HFConfigration.cloudPassword);
			HFLocalSaveHelper.getInstence().setAccesskey(
					HFConfigration.accessKey);
			HFLocalSaveHelper.getInstence().setIsregisted(true);
			ManagerFactory.getManager(context, IHFSFManager.class).syncRemoteModuleInfo(
					new SyncModuleEventListener() {

						@Override
						public void onSyncSuccess() {
							// TODO Auto-generated method stub
							notifyLoginEvent(true);
						}

						@Override
						public void onSyncErr() {
							// TODO Auto-generated method stub
							notifyLoginEvent(true);
						}
					});
			//[should remove these code later] the end 
			
			String sessionId = response.getSessionId();
			String userId = response.getPayload().getUserId();
			
			UserInfo userInfo;
			while ((userInfo = userInfoDao.getActiveUserInfo()) != null) {
				userInfo.setActive(false);
				userInfoDao.saveUserInfo(userInfo);
			}
			
			userInfo = userInfoDao.getUserInfo(userId);
			if (userInfo == null) {
				userInfo = new UserInfo();
				userInfo.setUserId(userId);
			}
			userInfo.setUserName(username);
			userInfo.setPassword(password);
			userInfo.setToken(sessionId);
			userInfo.setTokenExpiredTime(System.currentTimeMillis() + 3600000);
			userInfo.setActive(true);
			userInfo.setAccessKey(payload.getAccessKey());
			userInfoDao.saveUserInfo(userInfo);
			
			return sessionId;
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
	}

	private String getMacAddress() {
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) HFConfigration.appContex
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	@Override
	public void logout(String sessionId) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "logout");
//		if (!isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		
		UserLogoutRequest request = new UserLogoutRequest();
		request.setSessionId(sessionId);
		try {
			cloudSecurityManager.logout(request);
			
			//[should remove these code later]
			HFLocalSaveHelper.getInstence().setIsregisted(false);
			//[should remove these code later] the end 
			
			UserInfo userInfo;
			while ((userInfo = userInfoDao.getActiveUserInfo()) != null) {
				userInfo.setActive(false);
				userInfo.setPassword(null);
				userInfoDao.saveUserInfo(userInfo);
			}
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
		
		
		
//		HFLocalSaveHelper.getInstence().setIsregisted(false);
//		String logoutdata = "{'CID':10021,'SID':#SID#}";
//		logoutdata = logoutdata.replaceAll("#SID#", getsid());
//		String rsp = HttpProxy.reqByHttpPost(logoutdata);
//		setSid(null);
//		try {
//			JSONObject json = new JSONObject(rsp);
//			if (json.getInt("RC") != 1) {
//				throw new HFModuleException(json.getInt("RC"),
//						json.getString("RN"));
//			}
//			HFLocalSaveHelper.getInstence().setIsregisted(false);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			throw new HFModuleException(HFModuleException.ERR_JSON_DECODE,
//					e.getMessage());
//		}
	}

	@Override
	public String registerUser(UserInfo userInfo, String captcha) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "registerUser");
		
		UserPayload payload = new UserPayload();
		payload.setAccessKey(userInfo.getAccessKey());
		payload.setCaptcha(captcha);
		payload.setCellPhone(userInfo.getCellPhone());
		payload.setDisplayName(userInfo.getDisplayName());
		payload.setEmail(userInfo.getEmail());
		payload.setIdNumber(userInfo.getIdNumber());
		payload.setPassword(userInfo.getPassword());
		payload.setUserName(userInfo.getUserName());
		
		UserRegisterRequest registerRequest = new UserRegisterRequest();
		registerRequest.setPayload(payload);
		
		try {
			UserIdResponse response = cloudSecurityManager.registerUser(registerRequest);
			String userId = response.getPayload().getUserId();
			userInfo.setUserId(userId);
			userInfo.setAccessKey(payload.getAccessKey());
			userInfoDao.saveUserInfo(userInfo);
			return userId;
		} catch (CloudException e) {
			e.printStackTrace();
			throw new HFModuleException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public UserInfo getUser(String sessionId) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getUser");
//		if (!isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		
		UserGetRequest request = new UserGetRequest();
		request.setSessionId(sessionId);
		try {
			UserResponse response = cloudSecurityManager.getUser(request);
			UserPayload payload = response.getPayload();
			
//			UserInfo userInfo = new UserInfo();
//			userInfo.setAccessKey(HFConfigration.accessKey);
//			userInfo.setCellPhone(payload.getCellPhone());
//			userInfo.setCreateTime(payload.getCreateTime());
//			userInfo.setDisplayName(payload.getDisplayName());
//			userInfo.setEmail(payload.getEmail());
//			userInfo.setIdNumber(payload.getIdNumber());
//			userInfo.setUserName(payload.getUserName());
			
			UserInfo userInfo = userInfoDao.getUserInfoByToken(sessionId);
			if (userInfo == null) {
				userInfo = new UserInfo();
			}
			userInfo.setAccessKey(HFConfigration.accessKey);
			userInfo.setCellPhone(payload.getCellPhone());
			userInfo.setCreateTime(payload.getCreateTime());
			userInfo.setDisplayName(payload.getDisplayName());
			userInfo.setEmail(payload.getEmail());
			userInfo.setIdNumber(payload.getIdNumber());
			userInfo.setUserName(payload.getUserName());
			
			userInfoDao.saveUserInfo(userInfo);
			
			return userInfo;
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
		
//		String req = "{'CID':10231,'SID':'#SID#'} ";
//		req = req.replaceAll("#SID#", getsid());
//		String rsp = HttpProxy.reqByHttpPost(req);
//		JSONObject json;
//		try {
//			json = new JSONObject(rsp);
//			if (json.getInt("RC") == 1) {
//
//				JSONObject joRsp = json.getJSONObject("PL");
//				UserInfo result = new UserInfo();
//
//				if (!joRsp.isNull("id"))
//					result.setId(joRsp.getString("id"));
//				if (!joRsp.isNull("displayName")) {
//					result.setDisplayName(joRsp.getString("displayName"));
//					HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
//							.setUserNickName(result.getDisplayName());
//					HFConfigration.cloudUserNickName = result.getDisplayName();
//				}
//				if (!joRsp.isNull("userName")) {
//					result.setUserName(joRsp.getString("userName"));
//					HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
//							.setUserName(result.getUserName());
//					HFConfigration.cloudUserName = result.getUserName();
//				}
//				if (!joRsp.isNull("cellPhone")) {
//					result.setCellPhone(joRsp.getString("cellPhone"));
//					HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
//							.setPhone(result.getCellPhone());
//					HFConfigration.cloudUserPhone = result.getCellPhone();
//				}
//				if (!joRsp.isNull("email")) {
//					result.setEmail(joRsp.getString("email"));
//					HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
//							.setEmail(result.getEmail());
//					HFConfigration.cloudUserEmail = result.getEmail();
//				}
//				if (!joRsp.isNull("idNumber")) {
//					result.setIdNumber(joRsp.getString("idNumber"));
//				}
//				if (!joRsp.isNull("createTime")) {
//					result.setCreateTime(joRsp.getString("createTime"));
//				}
//				return result;
//			} else {
//				throw new HFModuleException(HFModuleException.ERR_GET_USER,
//						"can not get user");
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			throw new HFModuleException(HFModuleException.ERR_GET_USER,
//					"can not get user");
//		}

	}

	@Override
	public void setUser(String sessionId, UserInfo userInfo) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setUser");
//		if (!isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		
		UserPayload payload = new UserPayload();
		
//		UserInfo payload = new UserInfo();
		payload.setCellPhone(userInfo.getCellPhone());
		payload.setDisplayName(userInfo.getDisplayName());
		payload.setEmail(userInfo.getEmail());
		payload.setIdNumber(userInfo.getIdNumber());
		
		//user name cannot modified in cloud service
//		payload.setUserName(userInfo.getUserName());
		
		UserSetRequest request = new UserSetRequest();
		request.setSessionId(sessionId);
		request.setPayload(payload);
		try {
			cloudSecurityManager.setUser(request);
			
			UserInfo savedUserInfo = userInfoDao.getUserInfoByToken(sessionId);
			if (savedUserInfo != null) {
				savedUserInfo.setCellPhone(userInfo.getCellPhone());
				savedUserInfo.setDisplayName(userInfo.getDisplayName());
				savedUserInfo.setEmail(userInfo.getEmail());
				savedUserInfo.setIdNumber(userInfo.getIdNumber());
				userInfoDao.saveUserInfo(savedUserInfo);
			}
		} catch (CloudException e) {
			e.printStackTrace();
			throw new HFModuleException(e.getErrorCode(), e.getMessage());
		}
		
//		JSONObject req = new JSONObject();
//		JSONObject pl = new JSONObject();
//		try {
//
//			if (info.getDisplayName() != null)
//				pl.put("displayName", info.getDisplayName());
//			if (info.getUserName() != null)
//				pl.put("userName", info.getUserName());
//			if (info.getPassword() != null)
//				pl.put("password", info.getPassword());
//			if (info.getCellPhone() != null)
//				pl.put("cellPhone", info.getCellPhone());
//			if (info.getEmail() != null)
//				pl.put("email", info.getEmail());
//			if (info.getIdNumber() != null)
//				pl.put("idNumber", info.getIdNumber());
//
//			req.put("CID", 10221);
//			req.put("SID", getsid());
//			req.put("PL", pl);
//			String rsp = HttpProxy.reqByHttpPost(req.toString());
//
//			JSONObject jo = new JSONObject(rsp);
//			if (jo.isNull("RC")) {
//				throw new HFModuleException(HFModuleException.ERR_SET_USER,
//						"can not set user");
//			}
//			if (jo.getInt("RC") != 1) {
//				throw new HFModuleException(HFModuleException.ERR_SET_USER,
//						"can not set user");
//			}
//
//		} catch (JSONException e) {
//			throw new HFModuleException(HFModuleException.ERR_SET_USER,
//					"can not set user");
//		}
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteUser");
	}

	@Override
	public void changePassword(String old, String newpswd)
			throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "changePassword");
		if (!this.isCloudChannelLive()) {
			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
					"User is not online");
		}

		try {
			JSONObject joReq = new JSONObject();
			JSONObject pl = new JSONObject();
			joReq.put("CID", 10241);
			joReq.put("SID", getsid());
			pl.put("oldPwd", old);
			pl.put("newPwd", newpswd);
			joReq.put("PL", pl);

			String req = joReq.toString();

			String rsp = HttpProxy.reqByHttpPost(req);
			JSONObject jo = new JSONObject(rsp);

			if (jo.isNull("RC")) {
				throw new HFModuleException(HFModuleException.ERR_CHANGE_PSWD,
						"can not change passwd");
			}
			if (jo.getInt("RC") != 1) {
				throw new HFModuleException(HFModuleException.ERR_CHANGE_PSWD,
						"can not change passwd");
			}
		} catch (JSONException e) {
			throw new HFModuleException(HFModuleException.ERR_CHANGE_PSWD,
					"can not change passwd");
		}
	}

	@Override
	public void retrievePassword(MessageReceiver receiver)
			throws HFModuleException {
		
		Log.d("HFModuleManager", "retrievePassword");
//		if (!this.isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		
		RetrievePasswordPayload payload = new RetrievePasswordPayload();
		payload.setAccessKey(HFConfigration.accessKey);
		if (MessageReceiver.RECEIVER_TYPE_SMS == receiver.getReceiverType()) {
			payload.setSms(receiver.getAddress());
		}else {
			payload.setEmail(receiver.getAddress());	
		}
		
		try {
			cloudSecurityManager.retrievePassword(new RetrievePasswordRequest(payload));
		} catch (CloudException e) {
			e.printStackTrace();
			throw new HFModuleException(e.getErrorCode(), e.getMessage());
		}
//		JSONObject joReq = new JSONObject();
//		JSONObject pl = new JSONObject();
//		try {
//			joReq.put("CID", 10041);
//			if (receiverType == 1) {
//				pl.put("sms", receiverAddress);
//			} else if (receiverType == 2) {
//				pl.put("email", receiverAddress);
//			} else {
//				pl.put("sms", receiverAddress);
//			}
//			joReq.put("PL", pl);
//			String req = joReq.toString();
//			System.out.println(req);
//			String rsp = HttpProxy.reqByHttpPost(req);
//			System.out.println(rsp);
//			JSONObject jo = new JSONObject(rsp);
//			if (jo.isNull("RC")) {
//				throw new HFModuleException(HFModuleException.ERR_GET_PSWD,
//						"can not get passwd");
//			}
//			if (jo.getInt("RC") != 1) {
//				throw new HFModuleException(HFModuleException.ERR_GET_PSWD,
//						"can not cget passwd");
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			throw new HFModuleException(HFModuleException.ERR_GET_PSWD,
//					"can not cget passwd");
//		}

	}

	@Override
	public void setKeyValueInfo(KeyValueInfo kv) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setKeyValueInfo");
		if (!this.isCloudChannelLive()) {
			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
					"User is not online");
		}
		JSONObject joReq = new JSONObject();
		JSONObject pl = new JSONObject();
		try {
			joReq.put("PL", pl);
			joReq.put("CID", 30511);
			joReq.put("SID", getsid());
			pl.put("key", kv.key);
			pl.put("value", kv.value);

			String req = joReq.toString();

			String rsp = HttpProxy.reqByHttpPost(req);
			JSONObject jo = new JSONObject(rsp);

			if (jo.isNull("RC")) {
				throw new HFModuleException(HFModuleException.ERR_SET_KEYVALUE,
						"can not set keyvalue");
			}
			if (jo.getInt("RC") != 1) {
				throw new HFModuleException(HFModuleException.ERR_SET_KEYVALUE,
						"can not set keyValue");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_SET_KEYVALUE,
					"can not set keyValue");
		}
	}

	@Override
	public KeyValueInfo getKeyvalueInfo(String key) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getKeyvalueInfo");
		if (!this.isCloudChannelLive()) {
			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
					"User is not online");
		}
		try {
			JSONObject joReq = new JSONObject();
			JSONObject pl = new JSONObject();
			joReq.put("PL", pl);
			joReq.put("CID", 30521);
			joReq.put("SID", getsid());
			pl.put("key", key);

			String req = joReq.toString();
			String rsp = HttpProxy.reqByHttpPost(req);
			JSONObject jo = new JSONObject(rsp);

			if (jo.isNull("RC")) {
				throw new HFModuleException(HFModuleException.ERR_GET_KEYVALUE,
						"can not set keyvalue");
			}
			if (jo.getInt("RC") != 1) {
				JSONObject rspPL = jo.getJSONObject("PL");

				KeyValueInfo kv = new KeyValueInfo();
				kv.key = key;
				kv.value = rspPL.getString("value");
				return kv;
			} else {
				throw new HFModuleException(HFModuleException.ERR_GET_KEYVALUE,
						"can not set keyvalue");
			}
		} catch (JSONException e) {
			// TODO: handle exception
			throw new HFModuleException(HFModuleException.ERR_GET_KEYVALUE,
					"can not set keyvalue");
		}
	}

	@Override
	public void deleteKeyValueInfo(String key) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteKeyValueInfo");
		if (!this.isCloudChannelLive()) {
			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
					"User is not online");
		}
		try {
			JSONObject joReq = new JSONObject();
			JSONObject pl = new JSONObject();
			joReq.put("PL", pl);
			joReq.put("CID", 30531);
			joReq.put("SID", getsid());
			pl.put("key", key);

			String req = joReq.toString();

			String rsp = HttpProxy.reqByHttpPost(req);
			JSONObject jo = new JSONObject(rsp);

			if (jo.isNull("RC")) {
				throw new HFModuleException(
						HFModuleException.ERR_DELETE_KEYVALUE,
						"can not delete keyvalue");
			}
			if (jo.getInt("RC") != 1) {
				throw new HFModuleException(
						HFModuleException.ERR_DELETE_KEYVALUE,
						"can not delete keyvalue");
			}
		} catch (JSONException e) {
			throw new HFModuleException(HFModuleException.ERR_DELETE_KEYVALUE,
					"can not delete keyvalue");
		}
	}

	@Override
	public void registerEventListener(IHFModuleEventListener li) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "registerEventListener");
		this.eventListenerList.remove(li);
		this.eventListenerList.add(li);
	}

	@Override
	public ModuleInfo setModule(ModuleInfo moduleInfo) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setModule");
//		if (!this.isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		if (moduleInfo == null) {
			throw new IllegalArgumentException("moduleinfo is Null");
//			throw new HFModuleException(HFModuleException.ERR_SET_MODULE,
//					"your moduleinfo is null");
		}

		moduleInfo.setAccessKey(HFConfigration.accessKey);
		ModuleSetRequest request = new ModuleSetRequest(moduleInfo);
		
		try {
			ModuleInfoResponse response = cloudModuleManager.setModule(request);
		
			ModuleInfo responseModuleInfo = response.getPayload();
			responseModuleInfo.setLocalIp(moduleInfo.getLocalIp());
			getHFModuleHelper().addRemoteModuleInfo(responseModuleInfo);
			
			return responseModuleInfo;
		} catch (CloudException e1) {
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
		
//		try {
//			JSONObject joReq = new JSONObject();
//			JSONObject pl = new JSONObject(moduleInfo.toJson());
//			joReq.put("PL", pl);
//			joReq.put("CID", 30011);
//			joReq.put("SID", this.getsid());
//
//			String req = joReq.toString();
//			String rsp = null;
//			rsp = HttpProxy.reqByHttpPost(req);
//			JSONObject jo = new JSONObject(rsp);
//
//			if (jo.isNull("RC")) {
//				throw new HFModuleException(HFModuleException.ERR_SET_MODULE,
//						"can not set module");
//			}
//
//			if (jo.getInt("RC") == 1) {
//				JSONObject rspPL = jo.getJSONObject("PL");
//				ModuleInfo rspInfo = new ModuleInfo();
//				rspInfo.fromJson(pl);
//
//				rspInfo.setLocalIp(moduleInfo.getLocalIp());
//				getHFModuleHelper().addRemoteModuleInfo(rspInfo);
//
//				return rspInfo;
//			} else {
//				throw new HFModuleException(HFModuleException.ERR_SET_MODULE,
//						"can not set module");
//			}
//		} catch (JSONException e) {
//			throw new HFModuleException(HFModuleException.ERR_SET_MODULE,
//					"can not set module");
//		}
	}

	@Override
	public ModuleInfo getModule(String mac) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getModule");
//		if (!this.isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE,
//					"User is not online");
//		}
		
		ModuleInfo mi = getHFModuleHelper().getRemoteModuleInfoByMac(mac);
		if (mi == null) {
			throw new HFModuleException(
					HFModuleException.ERR_GET_REMOTE_MODULEINFO,
					"get moduleinfo from remote err");
		}
		if (mi.getModuleId() == null) {
			return mi;
		}
		
		ModuleIdPayload payload = new ModuleIdPayload();
		payload.setModuleId(mi.getModuleId());
		ModuleGetRequest request = new ModuleGetRequest(payload);
		try {
			ModuleInfoResponse response = cloudModuleManager.getModule(request);
			ModuleInfo responseModuleInfo = response.getPayload();
			responseModuleInfo.setLocalIp(mi.getLocalIp());
			getHFModuleHelper().addRemoteModuleInfo(responseModuleInfo);
			
			return responseModuleInfo;
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return mi;
//		try {
//			String reqTemplate = "{'PL':{'moduleId':'#moduleId#'},'CID':30031,'SID':'#SID#'}";
//			String req = reqTemplate.replaceFirst("#moduleId#", mi.getModuleId())
//					.replaceFirst("#SID#", getsid());
//
//			String rsp = HttpProxy.reqByHttpPost(req);
//			JSONObject jo = new JSONObject(rsp);
//
//			if (jo.isNull("RC")) {
//				throw new HFModuleException(
//						HFModuleException.ERR_GET_REMOTE_MODULEINFO,
//						"get moduleinfo from remote err");
//			}
//			if (jo.getInt("RC") == 1) {
//				JSONObject rspPL = jo.getJSONObject("PL");
//				ModuleInfo rspInfo = new ModuleInfo();
//				rspInfo.fromJson(rspPL);
//
//				if (mi != null) {
//					rspInfo.setLocalIp(mi.getLocalIp());
//				}
//				getHFModuleHelper().addRemoteModuleInfo(mi);
//				return rspInfo;
//			} else {
//				return mi;
//			}
//		} catch (JSONException e) {
//			return mi;
//		}
	}

	@Override
	public void deleteModule(String mac) throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteModule");
		

		try {
			ModuleInfo mi = getHFModuleHelper().getRemoteModuleInfoByMac(mac);
			// *******
			if (mi == null) {
				mi = getHFModuleHelper().getMyLocalModuleInfoByMac(mac);
				if (mi == null) {
					mi = getHFModuleHelper().getLocalModuleInfoByMac(mac);
					if (mi == null) {
						throw new HFModuleException(
								HFModuleException.ERR_DELETE_MODULE,
								"can not find this module");
					} else {
						getHFModuleHelper().removeLocalModuleInfoByMac(mac);
					}
				} else {
					getHFModuleHelper().removeMyLocalModuleInfoByMac(mac);
				}
			} else {
				if (mi.getModuleId() == null) {
					getHFModuleHelper().removeRemoteModuleInfoByMac(mac);
					return;
				}
				if (!this.isCloudChannelLive()) {
					getHFModuleHelper().removeRemoteModuleInfoByMac(mac);
					return ;
				}
				// ***
				String reqTemplate = "{'PL':{'moduleId':'#moduleId#'},'CID':30021,'SID':'#SID#'}";
				String req = reqTemplate.replaceFirst("#moduleId#", mi.getModuleId())
						.replaceFirst("#SID#", getsid());

				String rsp = HttpProxy.reqByHttpPost(req);
				JSONObject jo = new JSONObject(rsp);
				if (jo.isNull("RC")) {
					throw new HFModuleException(
							HFModuleException.ERR_DELETE_MODULE,
							"delete module err");
				}
				if (jo.getInt("RC") != 1) {
					throw new HFModuleException(
							HFModuleException.ERR_DELETE_MODULE,
							"delete module err");
				} else {
					getHFModuleHelper().removeRemoteModuleInfoByMac(mac);
					getHFModuleHelper().removeMyLocalModuleInfoByMac(mac);
					getHFModuleHelper().removeLocalModuleInfoByMac(mac);
					getHFModuleHelper().removeNewModuleInfoByMac(mac);
				}
			}
		} catch (JSONException e) {
			throw new HFModuleException(HFModuleException.ERR_DELETE_MODULE,
					"delete module err");
		}
	}

	@Override
	public List<ModuleInfo> getAllModule() throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getAllModule");
//		if (!this.isCloudChannelLive()) {
//			throw new HFModuleException(HFModuleException.ERR_COULD_NOT_ALIVE,
//					"sid == null");
//		}
		
		ModuleGetAllRequest request = new ModuleGetAllRequest();
		try {
			ModuleGetAllResponse response = cloudModuleManager.getAllModule(request);
			List<ModuleInfo> modules = response.getPayload(); 
			HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
			.getServerModuleInfoHelper().putAll(modules);
			return modules;
		} catch (CloudException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
		
//		return Collections.<ModuleInfo>emptyList();
//		
//		String reqTemplate = "{'CID':30051,'SID':'#SID#'}";
//		String req = reqTemplate.replaceFirst("#SID#", getsid());
//		String rsp = HttpProxy.reqByHttpPost(req);
//		try {
//			JSONObject json = new JSONObject(rsp);
//			ArrayList<ModuleInfo> result = new ArrayList<ModuleInfo>();
//			if (json.getInt("RC") == 1) {
//				JSONArray ja = json.getJSONArray("PL");
//				for (int i = 0; i < ja.length(); i++) {
//					ModuleInfo mi = new ModuleInfo();
//					mi.fromJson(ja.getJSONObject(i));
//					result.add(mi);
//				}
//				HFLocalSaveHelper.getInstence().getMainUserInfoHelper()
//						.getServerModuleInfoHelper().putAll(result);
//				return result;
//			} else {
//				throw new HFModuleException(json.getInt("RC"),
//						json.getString("RN"));
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
	}

	@Override
	public IHFModuleHelper getHFModuleHelper() {
		// TODO Auto-generated method stub
		//Log.d("HFModuleManager", "getHFModuleHelper");
		if (hfModuleManager == null) {
			hfModuleManager = new HFModuleHelper();
		}
		return hfModuleManager;
	}

	@Override
	public void unregisterEventListener(IHFModuleEventListener li) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "unregisterEventListener");
		this.eventListenerList.remove(li);
	}

	@Override
	public void removeAllListener() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "removeAllListener");
		this.eventListenerList.clear();
	}

	@Override
	public void startRemoteRefreshTimer() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "startRemoteRefreshTimer");
	}

	Runnable beatThread = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isAppRunning) { Log.d("beatThread", "beatThread..");
				byte[] data = "~@@Hello,Thing!**#".getBytes();
				ByteBuffer bf = ByteBuffer.allocate(3 + data.length);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put(data);
				DatagramPacket beat = new DatagramPacket(bf.array(),
						bf.array().length, HFConfigration.broudcastIp,
						HFConfigration.localUDPPort);

				IHFModuleHelper moduleHelper = getHFModuleHelper();
				moduleHelper.updatRemoteModuleLocalIp();
				moduleHelper.updatLocalMyModuleLocalIp();
				moduleHelper.updatLocalModuleLocalIp();
				moduleHelper.updatNewModuleLocalIp();
				try {
					localBeatSocket.send(beat);			
					if (HFLocalSaveHelper.getInstence().isIsregisted()) {
						if (getsid() == null) {
							login(HFConfigration.cloudUserName, HFConfigration.cloudPassword);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					initBroadCast();
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					changeDomain();
				}
//				Log.i("HFModuleManager", "sendBeat->"
//						+ HFConfigration.broudcastIp.getHostAddress() + ":"
//						+ HFConfigration.localUDPPort);
				
				try {
					Thread.sleep(HFConfigration.pulseInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				ByteTool.sleep(HFConfigration.pulseInterval);
			}
		}

		private void initBroadCast() {
			try {
				HFConfigration.broudcastIp = InetAddress
						.getByName(getBroadcastAddress());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		int domainNum = 0;

		private void changeDomain() {
			String[] domians = { "http://node-cn.iotworkshop.com/usvc/",
					"http://node-cn.iotworkshop.com/usvc/", "http://node-cn.iotworkshop.com/usvc/" };
			if (domainNum == 3)
				domainNum = 0;
			HFConfigration.cloudServiceUrl = domians[domainNum];
			HFLocalSaveHelper.getInstence().setServerDomain(
					HFConfigration.cloudServiceUrl);
			domainNum++;
		}
	};

	Runnable recvThread = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			byte[] buff = new byte[HFConfigration.macTMsgPacketSize];
			while (isAppRunning) {
				try {
					DatagramPacket recv = new DatagramPacket(buff, buff.length);
//					recv.setPort(HFConfigration.localUDPPort);
					localBeatSocket.receive(recv);
					byte[] data = ByteTool.copyOfRange(buff, 0,
							recv.getLength());
					String ip = recv.getAddress().getHostAddress();
					decode(data, ip); Log.d("HFModuleManager:", "ip:" + ip);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

		private synchronized void decode(byte[] data, String ip)
				throws HFModuleException {
			byte[] t1 = ByteTool.copyOfRange(data, 0, data.length);
			T1Message msg = new T1Message();
			msg.unpack(t1);
			String mac = msg.getH1().getMac();
			int deviceBelongType = 1;
			ModuleInfo mi = getHFModuleHelper()
					.getRemoteModuleInfoByMac(mac);
			if (mi == null) {
				mi = getHFModuleHelper()
						.getMyLocalModuleInfoByMac(mac);
				deviceBelongType = 2;
			}
			if (mi == null) {
				mi = getHFModuleHelper().getLocalModuleInfoByMac(mac);
				deviceBelongType = 3;
			}

			if (msg.getH1().getCmd() == 0x01) {
				if (msg.getH1().getFlag().isRegisted()) {
					if (mi == null) {
						// find new local Module
						Log.e("findLocalModule", mac);
						msg.setKey(HFConfigration.defLocalKey);
						msg.unpack(t1);
						mi = new ModuleInfo();
						mi.setMac(mac);
						mi.setLocalKey(HFConfigration.defLocalKey);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setLastTimestamp(new java.util.Date().getTime());
						mi.setAccessKey(HFConfigration.accessKey);
						getHFModuleHelper().addLocalModuleInfo(mi);
						notifyLocalModuleFind(mi);
						return;
					} else {
						// update My dev info
						msg.setKey(mi.getLocalKey());
						msg.unpack(t1);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setLastTimestamp(new java.util.Date().getTime());
						mi.setAccessKey(HFConfigration.accessKey);
						if (deviceBelongType == 1) {
							getHFModuleHelper().addRemoteModuleInfo(mi);
						}
						if (deviceBelongType == 2) {
							getHFModuleHelper().addMyLocalModuleInfo(mi);
						}
						if (deviceBelongType == 3) {
							getHFModuleHelper().addLocalModuleInfo(mi);
						}
						return;
					}
				} else {
					// new Module find
					if (mi != null) {
						Log.e("find unset Module of me", mac);
						mi.setMac(mac);
						mi.setLocalKey(AES.DEFAULT_KEY_128);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setLastTimestamp(new java.util.Date().getTime());
						mi.setAccessKey(HFConfigration.accessKey);
						mi = getHFModuleLocalManager()
								.setNewModuleLocalInfo(mi);
						if (deviceBelongType == 1) {
							getHFModuleHelper().addRemoteModuleInfo(mi);
						}
						if (deviceBelongType == 2) {
							getHFModuleHelper().addMyLocalModuleInfo(mi);
						}
						if (deviceBelongType == 3) {
							getHFModuleHelper().addLocalModuleInfo(mi);
						}
					} else {
						Log.e("find new Module", mac);
						mi = new ModuleInfo();
						mi.setMac(mac);
						mi.setLocalKey(AES.DEFAULT_KEY_128);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setLastTimestamp(new java.util.Date().getTime());
						mi.setAccessKey(HFConfigration.accessKey);
						getHFModuleHelper().removeNewModuleInfoByMac(mac);
						getHFModuleHelper().addNewModuleInfo(mi);
						notifyNewModuleFind(mi);
					}
				}
			} else {
				if (mi == null) {
					return;
				} else {
					msg.setKey(mi.getLocalKey());
					msg.unpack(t1);
					switch (msg.getH1().getCmd()) {
					case 0x10:
						T2Massage t2 = new T2Massage();
						t2.unpack(msg.getH2().getT2());
						if (t2.getFlag2() == 0x01) {
							// GPIO event
							GPIO g = new GPIO();
							ArrayList<GPIO> gpios = g.unpack(t2.getData());
							notifyGPIOEvent(mac, gpios);
						} else if (t2.getFlag2() == 0x02) {
							// timer event
							Log.e("UNReadyInterface", ByteTool
									.Byte2StringWithSpace(msg.getH2().getT2()));
						} else if ((t2.getFlag2() & 0x0f) == 0x03) {
							// ENTM
							Log.e("UNReadyInterface", ByteTool
									.Byte2StringWithSpace(msg.getH2().getT2()));
						} else {
							System.out.println("TAG1_EVENT3");
							Log.e("UNReadyInterface", ByteTool
									.Byte2StringWithSpace(msg.getH2().getT2()));
						}
						break;
					default:
						break;
					}
				}
			}
		}
	};

	private void notifyLoginEvent(boolean loginstat) {
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while (it.hasNext()) {
			it.next().onCloudLogin(loginstat);
		}
	}

	private void notifyNewModuleFind(ModuleInfo mi) {
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while (it.hasNext()) {
			it.next().onNewDevFind(mi);
		}
	}

	private void notifyLocalModuleFind(ModuleInfo mi) {
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while (it.hasNext()) {
			it.next().onLocalDevFind(mi);
		}
	}

	private void notifyGPIOEvent(String mac, ArrayList<GPIO> gpios) {
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while (it.hasNext()) {
			it.next().onGPIOEvent(mac);
		}
	}

	@Override
	public void startLocalTimer() throws HFModuleException {
		// TODO Auto-generated method stub
		this.isAppRunning = true;
		try {
			HFConfigration.broudcastIp = InetAddress
					.getByName(getBroadcastAddress());
			localBeatSocket = new DatagramSocket();
			localBeatSocket.setBroadcast(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_BROADCAST_GET,
					e.getMessage());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_BROADCAST_GET,
					e.getMessage());
		}
		new Thread(beatThread).start();
		new Thread(recvThread).start();
	}

	private String getBroadcastAddress() {
		WifiManager myWifiManager = (WifiManager) HFConfigration.appContex
				.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo myDhcpInfo = myWifiManager.getDhcpInfo();
		if (myDhcpInfo == null) {
			System.out.println("Could not get broadcast address");
			return "255.255.255.255";
		}
		int broadcast = (myDhcpInfo.ipAddress & myDhcpInfo.netmask)
				| ~myDhcpInfo.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		try {
			return InetAddress.getByAddress(quads).getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return "255.255.255.255";
		}
	}

	@Override
	public IHFModuleLocalManager getHFModuleLocalManager() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getHFModuleLocalManager");
		if (moduellocalmanager == null) {
			moduellocalmanager = new HFModuleLocalManager(localBeatSocket);
		}
		return moduellocalmanager;
	}

	@Override
	public void stopLocalTimer() {
		// TODO Auto-generated method stub
		this.isAppRunning = false;
		localBeatSocket.close();
	}

	public boolean isCloudChannelLive() {
		if (this.sid == null) {
			Log.e("isCloudChannelLive", "fasle");
			return false;
		} else {
			Log.e("isCloudChannelLive", getsid());
			return true;
		}
	}
}

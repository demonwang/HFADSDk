package com.hf.helper;

import java.util.HashMap;
import java.util.Random;


import android.content.Context;

import com.hf.ManagerFactory;
import com.hf.cloud.CloudService;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.manager.ICloudDeviceManager;
import com.hf.cloud.message.device.DeviceResponse;
import com.hf.cloud.message.device.DeviceSetRequest;
import com.hf.cmd.Flag;
import com.hf.cmd.Head1;
import com.hf.cmd.Head2;
import com.hf.cmd.T1Message;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFSendSyncCmdHelper;
import com.hf.lib.util.HexBin;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;
import com.hf.util.UdpProxy;

public class HFSendSyncCmdHelper implements IHFSendSyncCmdHelper{
	
	private Context context;
	private ICloudDeviceManager cloudDeviceManager;
	
	public HFSendSyncCmdHelper(Context context) {
		super();
		this.context = context;
		cloudDeviceManager = ManagerFactory.getManager(this.context, CloudService.class).getCloudDeviceManager();
	}

	@Override
	public byte[] sendLocalMsg(ModuleInfo mi,byte[] msg) throws HFModuleException {
		// TODO Auto-generated method stub
		T1Message t1 = new T1Message();
		Head1 h1 = new Head1();
		Head2 h2 = new Head2();
		t1.setH1(h1);
		t1.setH2(h2);
		Flag flag = new Flag();
		h1.setFlag(flag);
		h1.setCmd((byte) 0x11);
		flag.unpack((byte) 0x49);
		h1.setMac(mi.getMac());
		h2.setVer((byte) 0x01);
		h2.setRsv((byte) 0);
		h2.setSn(ByteTool.Int2Byte(new Random(2048).nextInt()));
		h2.setFid(ByteTool.Int2Byte(mi.getFactoryId()));
		h2.setDtype(ByteTool.Int2Byte(mi.getType()));
		h2.setT2(msg);
		t1.setKey(mi.getLocalKey());
		byte[] data  = t1.pack();
		byte[] rspData = UdpProxy.reqByUdp(mi.getLocalIp(), data);
		t1.unpack(rspData);
		if(t1.getH1().getCmd() != 0x11){
			throw new HFModuleException(HFModuleException.ERR_SEND_LOCAL_MSG, "send ctrl msg to module err");
		}
		if(t1.getH1().getFlag().pack()!=0x4B){
			throw new HFModuleException(HFModuleException.ERR_SEND_LOCAL_MSG, "send ctrl msg to module err");
		}
		return t1.getH2().getT2();
	}

	@Override
	public byte[] sendServerMsg(ModuleInfo mi, byte[] msg) throws HFModuleException {
		// TODO Auto-generated method stub
//		if(!ManagerFactory.getManager(context, IHFModuleManager.class).isCloudChannelLive()){
//			throw new HFModuleException(HFModuleException.ERR_USER_OFFLINE, "send ctrl msg to module err");
//		}
		HashMap<String, String> payload = new HashMap<String, String>();
		payload.put(mi.getMac(), HexBin.bytesToString(msg));
		DeviceSetRequest request = new DeviceSetRequest();
		request.setSessionId(UserHelper.getCurrentUserToken(context));
		request.setPayload(payload);
		try {
			DeviceResponse response = cloudDeviceManager.setDevice(request);
			String value = response.getPayload().get(mi.getMac());
			if (value == null) {
				return null;
			}else {
				return HexBin.stringToBytes(response.getPayload().get(mi.getMac()));
			}
		} catch (CloudException e1) {
			e1.printStackTrace();
			throw new HFModuleException(e1.getErrorCode(), e1.getMessage());
		}
//		
//		
//			JSONObject joReq = new JSONObject();
//			JSONObject pl = new JSONObject();
//		
//			try {
//				joReq.put("CID", 20031);
//				joReq.put("SID", HFModuleManager.sid);
//				pl.put(mi.getMac(), ByteTool.bytes2HexString(msg));
//				joReq.put("PL", pl);
//				
//				String req = joReq.toString();
//
//				String rsp = HttpProxy.reqByHttpPost(req);
//				JSONObject jo = new JSONObject(rsp);
//
//				if(jo.isNull("RC")){
//					throw new HFModuleException(HFModuleException.ERR_SEND_SERVER_MSG, "send ctrl msg to module err");
//				}
//				if (jo.getInt("RC") == 1) {
//
//					JSONObject joPl = jo.getJSONObject("PL");
//					if(joPl.isNull(mi.getMac())){
//						throw new HFModuleException(HFModuleException.ERR_SEND_SERVER_MSG, "send ctrl msg to module err");
//					}else{
//						return ByteTool.hexStringToBytes(jo.getString(mi.getMac()));
//					}
//				} else {
//					throw new HFModuleException(HFModuleException.ERR_SEND_SERVER_MSG, "send ctrl msg to module err");
//				}			
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				throw new HFModuleException(HFModuleException.ERR_SEND_SERVER_MSG, "send ctrl msg to module err");
//			}
	}

	@Override
	public byte[] sendAuto(ModuleInfo mi, byte[] msg) throws HFModuleException {
		// TODO Auto-generated method stub
		if(mi.getLocalIp()==null){
			return sendServerMsg(mi, msg);
		}else{
			try {
				return sendLocalMsg(mi, msg);
			} catch (HFModuleException e) {
				e.printStackTrace();
				return sendServerMsg(mi, msg);
			}
		}
	}

}

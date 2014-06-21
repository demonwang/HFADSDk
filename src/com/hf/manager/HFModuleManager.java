package com.hf.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

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
import com.hf.cmd.T1CtrlMassage;
import com.hf.cmd.T1Message;
import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.helper.HFModuleHelper;
import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
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
	private String sid;
	private ArrayList<IHFModuleEventListener> eventListenerList = new ArrayList<IHFModuleEventListener>();
	private IHFModuleHelper hfModuleManager = null;
	
	private void setSid(String sid){
		this.sid = sid;
	}
	private String getsid(){
		return this.sid;
	}
	
	
	@Override
	public synchronized void login() throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "login");
		String logindata = "{'PL':{'accessKey':'#acesskey#','userName':'#username#','password':'#password#','agingTime':300000,'mac':'#mac#','clientName':'#phoneName#'},'CID':10011}";
		String phoneName = android.os.Build.MANUFACTURER;
		logindata = logindata.replaceAll("#acesskey#", HFConfigration.accessKey);
		logindata = logindata.replaceAll("#username#", HFConfigration.cloudUserName);
		logindata = logindata.replaceAll("#password#", HFConfigration.cloudPassword);
		logindata = logindata.replaceAll("#mac#", getMacAddress());
		logindata = logindata.replaceAll("#phoneName#", phoneName);
		String rsp = HttpProxy.reqByHttpPost(logindata);
		try {
			JSONObject json = new JSONObject(rsp);
			if(json.getInt("RC") == 1){
				setSid(json.getString("SID"));
				HFLocalSaveHelper.getInstence().getMainUserInfoHelper().setUserName(HFConfigration.cloudUserName);
				HFLocalSaveHelper.getInstence().getMainUserInfoHelper().setPswd(HFConfigration.cloudPassword);
				HFLocalSaveHelper.getInstence().setAccesskey(HFConfigration.accessKey);
				HFLocalSaveHelper.getInstence().setIsregisted(true);
				ManagerFactory.getFSFManager().syncRemoteModuleInfo(new SyncModuleEventListener() {
					
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
				
			}else{
				throw new HFModuleException(json.getInt("RC"),json.getString("RN"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_JSON_DECODE, e.getMessage());
		}
	}
	
	private  String getMacAddress() {
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
	public void logout() throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "logout");
		if(!isCloudChannelLive()){
			return ;
		}
		HFLocalSaveHelper.getInstence().setIsregisted(false);
		String logoutdata = "{'CID':10021,'SID':#SID#}";
		logoutdata = logoutdata.replaceAll("#SID#", getsid());
		String rsp = HttpProxy.reqByHttpPost(logoutdata);
		setSid(null);
		try {
			JSONObject json = new JSONObject(rsp);
			if(json.getInt("RC") != 1){
				throw new HFModuleException(json.getInt("RC"),json.getString("RN"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_JSON_DECODE, e.getMessage());
		}
	}

	@Override
	public void registerUser() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "registerUser");
	}

	@Override
	public void getUser() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getUser");
	}

	@Override
	public void setUser() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setUser");
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteUser");
	}

	@Override
	public void changePassword(String old, String newpswd) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "changePassword");
	}

	@Override
	public void retrievePassword(String email) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "retrievePassword");
	}

	@Override
	public void setKeyValueInfo(KeyValueInfo kv) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setKeyValueInfo");
	}

	@Override
	public KeyValueInfo getKeyvalueInfo(String key) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getKeyvalueInfo");
		return null;
	}

	@Override
	public void deleteKeyValueInfo(String key) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteKeyValueInfo");
	}

	@Override
	public IHFModuleLocalManager getHFModuleLocalManager() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getHFModuleLocalManager");
		return null;
	}

	@Override
	public void registerEventListener(IHFModuleEventListener li) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "registerEventListener");
		this.eventListenerList.remove(li);
		this.eventListenerList.add(li);
	}

	@Override
	public void setModule(ModuleInfo mi) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "setModule");
		
	}

	@Override
	public void getModule(String mac) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getModule");
	}

	@Override
	public void deleteModule(String mac) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "deleteModule");
	}

	@Override
	public ArrayList<ModuleInfo> getAllModule() throws HFModuleException {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getAllModule");
		if(!this.isCloudChannelLive()){
			throw new HFModuleException(HFModuleException.ERR_COULD_NOT_ALIVE, "sid == null");
		}
		String reqTemplate = "{'CID':30051,'SID':'#SID#'}";
		String req = reqTemplate.replaceFirst("#SID#", getsid());
		String rsp  =  HttpProxy.reqByHttpPost(req);
		try {
			JSONObject json = new JSONObject(rsp);
			ArrayList<ModuleInfo> result = new ArrayList<ModuleInfo>();
			if(json.getInt("RC") == 1){
				JSONArray ja = json.getJSONArray("PL");
				for (int i = 0; i < ja.length(); i++) {
					ModuleInfo mi = new ModuleInfo();
					mi.fromJson(ja.getJSONObject(i));
					result.add(mi);
				}
				HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getServerModuleInfoHelper().putAll(result);
				return result;
			}else{
				throw new HFModuleException(json.getInt("RC"),json.getString("RN"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IHFModuleHelper getHFModuleHelper() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getHFModuleHelper");
		if(hfModuleManager == null){
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
			while(isAppRunning){
				byte[] data = "~@@Hello,Thing!**#".getBytes();
				ByteBuffer bf = ByteBuffer.allocate(3+data.length);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put((byte) 0x01);
				bf.put(data);
				DatagramPacket beat = new DatagramPacket(bf.array(), bf.array().length,HFConfigration.broudcastIp,HFConfigration.localUDPPort);
				
				try {
					localBeatSocket.send(beat);
					if(HFLocalSaveHelper.getInstence().isIsregisted()){
						if(getsid() == null){
							login();
						}
					}
					ManagerFactory.getManager().getHFModuleHelper().updatRemoteModuleLocalIp();
					ManagerFactory.getManager().getHFModuleHelper().updatLocalMyModuleLocalIp();
					ManagerFactory.getManager().getHFModuleHelper().updatLocalModuleLocalIp();
					ManagerFactory.getManager().getHFModuleHelper().updatNewModuleLocalIp();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					initBroadCast();
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					changeDomain();
				}
				Log.i("HFModuleManager", "sendBeat->"+HFConfigration.broudcastIp.getHostName()+":"+HFConfigration.localUDPPort);
				ByteTool.sleep(HFConfigration.pulseInterval);
			}
		}
		
		private void initBroadCast(){
			try {
				HFConfigration.broudcastIp = InetAddress.getByName(getBroadcastAddress());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		int domainNum = 0;
		
		private void changeDomain(){
			String[] domians = {"http://115.29.164.59/usvc/","http://115.29.164.59/usvc/","http://115.29.164.59/usvc/"};
			if(domainNum == 3)
				domainNum = 0;
			HFConfigration.cloudServiceUrl = domians[domainNum];
			HFLocalSaveHelper.getInstence().setServerDomain(HFConfigration.cloudServiceUrl);
			domainNum++;
		}
	};
	
	Runnable recvThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			byte[] buff = new byte[HFConfigration.macTMsgPacketSize];
			while(isAppRunning){
				try {
					DatagramPacket recv = new DatagramPacket(buff, buff.length);
					localBeatSocket.receive(recv);
					byte[] data = ByteTool.copyOfRange(buff, 0, recv.getLength());
					decode(data,recv.getAddress().getHostAddress());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		private  void decode(byte[] data,String ip) throws HFModuleException{
			byte[] t1 = data;
			T1Message msg = new T1Message();
			msg.unpack(t1);
			String mac = msg.getH1().getMac();
			ModuleInfo mi = HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getServerModuleInfoHelper().get(mac);
			if(mi == null){
				mi = HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getLocalModuleInfoHelper().get(mac);
			}
			if(msg.getH1().getCmd() == 0x01){
				if(msg.getH1().getFlag().isRegisted()){
					if(mi == null){
						//find local Module
						msg.setKey(HFConfigration.defLocalKey);
						msg.unpack(t1);
						mi = new ModuleInfo();
						mi.setMac(mac);
						mi.setLocalKey(HFConfigration.defLocalKey);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setAccessKey(HFConfigration.accessKey);
						getHFModuleHelper().removeLocalModuleInfoByMac(mac);
						getHFModuleHelper().addLocalModuleInfo(mi);
						notifyLocalModuleFInd(mi);
						return ;
					}else{
						//update My dev info
						msg.setKey(mi.getLocalKey());
						msg.unpack(t1);
						mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
						mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
						mi.setLocalIp(ip);
						mi.setAccessKey(HFConfigration.accessKey);
						getHFModuleHelper().addRemoteModuleInfo(mi);
						return ;
					}
				}else{
					//new Module find 
					mi = new ModuleInfo();
					mi.setMac(mac);
					mi.setLocalKey(AES.DEFAULT_KEY_128);
					mi.setType(ByteTool.Byte2Int(msg.getH2().getDtype()));
					mi.setFactoryId(ByteTool.Byte2Int(msg.getH2().getFid()));
					mi.setLocalIp(ip);
					mi.setAccessKey(HFConfigration.accessKey);
					getHFModuleHelper().removeNewModuleInfoByMac(mac);
					getHFModuleHelper().addNewModuleInfo(mi);
					notifyNewModuleFind(mi);
				}
			}else{
				if(mi == null){
					return ;
				}else{
					msg.setKey(mi.getLocalKey());
					msg.unpack(t1);
					switch (msg.getH1().getCmd()) {
					case 0x10:
						Log.e("EVENT", ByteTool.Byte2StringWithSpace(msg.getH2().getT2()));
						break;						
					default:
						break;
					}
				}
				
				
			}
			
		}
	};
	
	
	private void notifyLoginEvent(boolean loginstat){
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while(it.hasNext()){
			it.next().onCloudLogin(loginstat);
		}
	}
	
	private void notifyNewModuleFind(ModuleInfo mi){
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while(it.hasNext()){
			it.next().onNewDevFind(mi);
		}
	}
	
	private void notifyLocalModuleFInd(ModuleInfo mi){
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while(it.hasNext()){
			it.next().onLocalDevFind(mi);
		}
	}
	
	private void notifyGPIOEvent(String mac){
		Iterator<IHFModuleEventListener> it = this.eventListenerList.iterator();
		while(it.hasNext()){
			it.next().onGPIOEvent(mac);
		}
	}
	@Override
	public void startLocalTimer() throws HFModuleException {
		// TODO Auto-generated method stub
		this.isAppRunning = true;
		try {
			HFConfigration.broudcastIp = InetAddress.getByName(getBroadcastAddress());
			localBeatSocket = new DatagramSocket();
			localBeatSocket.setBroadcast(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_BROADCAST_GET, e.getMessage());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			throw new HFModuleException(HFModuleException.ERR_BROADCAST_GET, e.getMessage());
		}
		new Thread(beatThread).start();
		new Thread(recvThread).start();
	}
	
	private String  getBroadcastAddress(){
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
	public void stopLocalTimer() {
		// TODO Auto-generated method stub
		this.isAppRunning = false;
		localBeatSocket.close();
	}
	
	public boolean isCloudChannelLive() {
		if (this.sid == null) {
			return false;
		} else {
			return true;
		}
	}
}

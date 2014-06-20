package com.hf.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFModuleEventListener;
import com.hf.itf.IHFModuleHelper;
import com.hf.itf.IHFModuleLocalManager;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;
import com.hf.util.HttpProxy;

public class HFModuleManager implements IHFModuleManager {
	private DatagramSocket localBeatSocket;
	private boolean isAppRunning = false;
	private String sid;
	private void setSid(String sid){
		this.sid = sid;
	}
	private String getsid(){
		return this.sid;
	}
	
	
	@Override
	public void login() throws HFModuleException {
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
		String logoutdata = "{'CID':10021,'SID':#SID#}";
		logoutdata = logoutdata.replaceAll("#SID#", getsid());
		String rsp = HttpProxy.reqByHttpPost(logoutdata);
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
	public ArrayList<ModuleInfo> getAllModule() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getAllModule");
		return null;
	}

	@Override
	public IHFModuleHelper getHFModuleHelper(IHFModuleManager manager) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getHFModuleHelper");
		return null;
	}

	@Override
	public IHFSFManager getFSFManager(IHFModuleManager manager) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "getFSFManager");
		return null;
	}

	@Override
	public void unregisterEventListener(IHFModuleEventListener li) {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "unregisterEventListener");
	}

	@Override
	public void removeAllListener() {
		// TODO Auto-generated method stub
		Log.d("HFModuleManager", "removeAllListener");
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
				DatagramPacket beat = new DatagramPacket(data, data.length,HFConfigration.broudcastIp,HFConfigration.localUDPPort);
				try {
					localBeatSocket.send(beat);
					if(HFLocalSaveHelper.getInstence().isIsregisted()){
						if(getsid() == null){
							login();
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
		}
	};
	
	Runnable recvThread = new Runnable() {
		byte[] buff = new byte[HFConfigration.macTMsgPacketSize];
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isAppRunning){
				DatagramPacket recv = new DatagramPacket(buff, buff.length);
				try {
					localBeatSocket.receive(recv);
					Log.i("HFModuleManager",new String(buff,0,recv.getLength()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	@Override
	public void startLocalTimer() throws SocketException {
		// TODO Auto-generated method stub
		this.isAppRunning = true;
		try {
			HFConfigration.broudcastIp = InetAddress.getByName(getBroadcastAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localBeatSocket = new DatagramSocket(HFConfigration.localUDPPort);
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

}

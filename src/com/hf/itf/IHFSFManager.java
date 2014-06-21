package com.hf.itf;

import java.net.SocketException;

import com.hf.util.HFModuleException;

import android.content.Context;

public interface IHFSFManager {
	
	public static int HISF_FIRSTRUN = 1;
	public static int HISF_UNLOGIN = 2;
	public static int HISF_LOCAL_MODE = 3;
	public static int HISF_SERVER_MODE = 4;
	public static int HISF_REGIST_OK = 5;
	public static int HISF_REGIST_NOK = 6;
	
	public int HISF_Start() throws HFModuleException;
	public int HISF_Login() throws HFModuleException;
	public int HISF_Register();
	public int HISF_Refresh();
	void syncRemoteModuleInfo(SyncModuleEventListener li);
	
	interface startEventListener{
		void onResult(int rs);
	}
	interface SyncModuleEventListener{
		void onSyncSuccess();
		void onSyncErr();
	}
}

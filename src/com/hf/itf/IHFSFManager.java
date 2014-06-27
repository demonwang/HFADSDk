package com.hf.itf;

import com.hf.util.HFModuleException;

public interface IHFSFManager {
	
	public static int HISF_FIRSTRUN = 1;
	public static int HISF_UNLOGIN = 2;
	public static int HISF_LOCAL_MODE = 3;
	public static int HISF_SERVER_MODE = 4;
	public static int HISF_REGIST_OK = 5;
	public static int HISF_REGIST_NOK = 6;
	/**
	 * Return start state 
	 * if HISF_FIRSTRUN
	 * 	please login or register
	 * else if HISF_LOCAL_MODE
	 * 	login err or unlogined
	 * else if HISF_SERVER_MODE
	 * 	login ok
	 * @return HISF_FIRSTRUN  HISF_LOCAL_MODE HISF_SERVER_MODE
	 * @throws HFModuleException
	 */
	public int HISF_Start() throws HFModuleException;
	/**
	 * unDone
	 * @return 
	 * @throws HFModuleException
	 */
	public int HISF_Login() throws HFModuleException;
	/**
	 * 
	 * @return
	 */
	public int HISF_Register();
	/**
	 * 
	 * @return
	 */
	public int HISF_Refresh();
	/**
	 * 
	 * sync user AddedModule to Server During  LocalMode 
	 * @param li
	 */
	void syncRemoteModuleInfo(SyncModuleEventListener li);
	/**
	 * 
	 * @author Administrator
	 *
	 */
	interface startEventListener{
		void onResult(int rs);
	}
	/**
	 * syncRemoteModuleInfo event
	 * @author Administrator
	 *
	 */
	interface SyncModuleEventListener{
		void onSyncSuccess();
		void onSyncErr();
	}
}

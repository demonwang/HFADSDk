package com.hf.manager;

import java.net.SocketException;
import java.util.ArrayList;

import android.content.Context;

import com.hf.ManagerFactory;
import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFSFManager;
import com.hf.util.HFModuleException;

public class HISFManager implements IHFSFManager{
	private int START_MODE = HISF_LOCAL_MODE;
	@Override
	public int HISF_Start() throws SocketException {
		// TODO Auto-generated method stub
		HFLocalSaveHelper.getInstence().init();
		HFLocalSaveHelper.getInstence().setIsfristRun(false);
		if(HFLocalSaveHelper.getInstence().isIsfristRun()){
			return HISF_FIRSTRUN;
		}
		if(HFLocalSaveHelper.getInstence().isIsregisted()){
			HFLocalSaveHelper.getInstence().loadConfigration();
			ManagerFactory.getManager().startLocalTimer();
			START_MODE = HISF_Login();			
			if(START_MODE == HISF_SERVER_MODE){
				addMyLocalModuleToServer();
			}
			return START_MODE;
		}else{
			return HISF_UNLOGIN;
		}
	}

	@Override
	public int HISF_Login() {
		// TODO Auto-generated method stub
		try {
			ManagerFactory.getManager().login();
			HFLocalSaveHelper.getInstence().getMainUserInfoHelper().setUserName(HFConfigration.cloudUserName);
			HFLocalSaveHelper.getInstence().getMainUserInfoHelper().setPswd(HFConfigration.cloudPassword);
			HFLocalSaveHelper.getInstence().setAccesskey(HFConfigration.accessKey);
			HFLocalSaveHelper.getInstence().setIsregisted(true);
			return HISF_SERVER_MODE;
		} catch (HFModuleException e) {
			// TODO: handle exception
			return  HISF_LOCAL_MODE;
		}
	}
	
	@Override
	public int HISF_Register() {
		// TODO Auto-generated method stub
		try {
			ManagerFactory.getManager().registerUser();
			
			return HISF_REGIST_OK;
		} catch (Exception e) {
			// TODO: handle exception
			return HISF_REGIST_NOK;
		}
	}

	@Override
	public int HISF_Refresh() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private void addMyLocalModuleToServer(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<ModuleInfo> locals  = HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getLocalModuleInfoHelper().getAll();
				for(ModuleInfo mi : locals){
					
					try {
						ManagerFactory.getManager().setModule(mi);
						HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getServerModuleInfoHelper().put(mi.getMac(), mi);
						HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getLocalModuleInfoHelper().remove(mi.getMac());
					} catch (Exception e) {
						// TODO: handle exception
					}			
				}	
			}
		}).start();
	}
}

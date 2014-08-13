package com.hf.manager;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.hf.ManagerFactory;
import com.hf.dao.IUserInfoDao;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.util.HFModuleException;

public class HISFManager implements IHFSFManager{
	private int START_MODE = HISF_LOCAL_MODE;
	
	private Context context;
	
	public HISFManager(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int HISF_Start() throws HFModuleException {
		// TODO Auto-generated method stub
		HFLocalSaveHelper.getInstence().init();
			HFLocalSaveHelper.getInstence().setIsfristRun(false);
		if(HFLocalSaveHelper.getInstence().isIsfristRun()){
			return HISF_FIRSTRUN;
		}
		
		if(HFLocalSaveHelper.getInstence().isIsregisted()){
			return HISF_SERVER_MODE;
		}else{
			return HISF_LOCAL_MODE;
		}
	}

	@Override
	public int HISF_Login() throws HFModuleException {
		// TODO Auto-generated method stub
//		ManagerFactory.getManager().login();
		return 0;
	}
	
	@Override
	public int HISF_Register() {
		// TODO Auto-generated method stub
		try {
//			ManagerFactory.getInstance().getModuleManager().registerUser();
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

	@Override
	public void syncRemoteModuleInfo(final SyncModuleEventListener li) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("syncRemoteModuleInfo", "syncRemoteModuleInfo");
				ArrayList<ModuleInfo> locals  = HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getLocalModuleInfoHelper().getAll();
				for(ModuleInfo mi : locals){
					try {
						ManagerFactory.getManager(context, IHFModuleManager.class).setModule(mi);
						HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getServerModuleInfoHelper().put(mi.getMac(), mi);
						HFLocalSaveHelper.getInstence().getMainUserInfoHelper().getLocalModuleInfoHelper().remove(mi.getMac());						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}			
				}	
				
				IHFModuleManager moduleManager = ManagerFactory.getManager(context, IHFModuleManager.class);
				IUserInfoDao userInfoDao = moduleManager.getUserInfoDao();
				UserInfo currentUserInfo = userInfoDao.getActiveUserInfo();
				if (currentUserInfo!=null && currentUserInfo.isTokenValid()) {

					try {
						moduleManager.getAllModule(currentUserInfo.getToken());
						li.onSyncSuccess();
					} catch (HFModuleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						li.onSyncErr();
					}
				}
			}
		}).start();
	}
}

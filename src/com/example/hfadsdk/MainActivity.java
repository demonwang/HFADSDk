package com.example.hfadsdk;

import java.net.InetAddress;

import com.hf.ManagerFactory;
import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFModuleEventListener;
import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.manager.HFModuleLocalManager;
import com.hf.smartlink.HFSMTLKHelper;
import com.hf.util.HFModuleException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements IHFModuleEventListener{

		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			Button asd = (Button) getActivity().findViewById(R.id.heihie);
			asd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), ModuleList.class);
					startActivity(i);
				}
			});
			
			final IHFModuleManager moduleManager = ManagerFactory.getManager(getActivity(), IHFModuleManager.class);
			moduleManager.registerEventListener(this);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						HFConfigration.appContex = getActivity();
//						int Mode  = ManagerFactory.getFSFManager().HISF_Start();
//						
//						if(Mode == IHFSFManager.HISF_LOCAL_MODE){
//							Log.e("smart", "HISF_LOCAL_MODE");
//							ManagerFactory.getManager().startLocalTimer();
//						}
//						
//						if(Mode == IHFSFManager.HISF_SERVER_MODE){
//							//start working
//							HFLocalSaveHelper.getInstence().loadConfigration();
//							ManagerFactory.getManager().startLocalTimer();
//							Log.e("smart", "HISF_SERVER_MODE");
//						}
//						
//						if(Mode == IHFSFManager.HISF_FIRSTRUN){
//							//��һ�� ����
//							HFConfigration.cloudUserName = "demon001";
//							HFConfigration.cloudPassword = "123456";
//							ManagerFactory.getManager().login();
//							ManagerFactory.getManager().startLocalTimer();
//							Log.e("smart", "HISF_FIRSTRUN");
//						}
						
						HFConfigration.broudcastIp = InetAddress.getByName("255.255.255.255");
//						moduleManager.startLocalTimer();
						moduleManager.getHFModuleLocalManager().getHFSMTLKHelper().startSmartlinkV30("19860608");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}

		@Override
		public void onEvent(String mac, byte[] t2data) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onEvent");
		}

		@Override
		public void onCloudLogin(boolean loginstat) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onCloudLogin");
			ManagerFactory.getManager(getActivity(), IHFModuleManager.class).unregisterEventListener(this);
			Intent i = new Intent(getActivity(), ModuleList.class);
			startActivity(i);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			}).start();
		}

		@Override
		public void onCloudLogout() {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onCloudLogout");
		}

		@Override
		public void onNewDevFind(ModuleInfo mi) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onNewDevFind");
		}

		@Override
		public void onLocalDevFind(ModuleInfo mi) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onLocalDevFind");
		}

		@Override
		public void onGPIOEvent(String mac) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onGPIOEvent");
		}

		@Override
		public void onTimerEvent(String mac, byte[] t2data) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onTimerEvent");
		}

		@Override
		public void onUARTEvent(String mac, byte[] userData, boolean chanle) {
			// TODO Auto-generated method stub
			Log.e("Fragment", "onUARTEvent");
		}
	}
}

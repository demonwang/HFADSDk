package com.example.hfadsdk;

import java.net.SocketException;

import com.hf.ManagerFactory;
import com.hf.data.HFConfigration;
import com.hf.helper.HFLocalSaveHelper;
import com.hf.itf.IHFSFManager;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

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
	public static class PlaceholderFragment extends Fragment {

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
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						HFConfigration.appContex = getActivity();
						int Mode  = ManagerFactory.getFSFManager().HISF_Start();
						if(Mode == IHFSFManager.HISF_LOCAL_MODE){
							Log.e("smart", "HISF_LOCAL_MODE");
						}
						if(Mode == IHFSFManager.HISF_SERVER_MODE){
							//start working
							Log.e("smart", "HISF_SERVER_MODE");
						}
						
						if(Mode == IHFSFManager.HISF_UNLOGIN){
							HFConfigration.cloudUserName = "demon001";
							HFConfigration.cloudPassword = "123456";
							ManagerFactory.getFSFManager().HISF_Login();
							ManagerFactory.getManager().startLocalTimer();
							Log.e("smart", "HISF_UNLOGIN");
						}
						
						if(Mode == IHFSFManager.HISF_FIRSTRUN){
							HFConfigration.cloudUserName = "demon001";
							HFConfigration.cloudSharePassword = "123456";
							ManagerFactory.getFSFManager().HISF_Login();
							ManagerFactory.getManager().startLocalTimer();
							Log.e("smart", "HISF_FIRSTRUN");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

}

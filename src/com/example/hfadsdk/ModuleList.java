package com.example.hfadsdk;

import java.util.ArrayList;
import java.util.Iterator;

import com.hf.ManagerFactory;
import com.hf.data.HFConfigration;
import com.hf.helper.HFModuleHelper;
import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.itf.IHFModuleHelper;
import com.hf.util.ByteTool;
import com.hf.util.HFModuleException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ModuleList extends Activity {
	private ListView modulelist;
	ArrayList<ModuleInfo> mis = new ArrayList<ModuleInfo>();
	ArrayList<ModuleInfo> remote;
	ArrayList<ModuleInfo> mylocal;
	ArrayList<ModuleInfo> local;
	ArrayList<ModuleInfo> New;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.module_list_layout);
		modulelist = (ListView) findViewById(R.id.modulelist);

		IHFModuleHelper moduleHelper = ManagerFactory.getInstance().getModuleManager().getHFModuleHelper();
		remote = moduleHelper.getAllRemoteModuleInfo();
		mylocal = moduleHelper.getMyLocalModuleInfoAll();
		local = moduleHelper.getAllLocalModuleInfo();
		New = moduleHelper.getAllNewModuleInfo();

		mis.addAll(remote);
		mis.addAll(mylocal);
		mis.addAll(local);
		mis.addAll(New);

		modulelist.setAdapter(muadpt);
		muadpt.notifyDataSetChanged();
		modulelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							ManagerFactory.getInstance().getModuleManager().deleteModule(mis.get(position).getMac());
						} catch (HFModuleException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		startTest();
	}

	private void startTest() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {/*
				// TODO Auto-generated method stub
				try {
					UserInfo mi = ManagerFactory.getManager().getUser();
					System.out.println(mi.toString());
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ManagerFactory.getManager().changePassword("123456",
							"123456");
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					KeyValueInfo kv = new KeyValueInfo();
					kv.key = "key";
					kv.value = "value";
					ManagerFactory.getManager().setKeyValueInfo(kv);
				} catch (HFModuleException e) {
					e.printStackTrace();
				}
				try {
					ManagerFactory.getManager().getKeyvalueInfo("key");
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ManagerFactory.getManager().deleteKeyValueInfo("key");
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ManagerFactory.getManager().getModule("ACCFACCF3389");
				} catch (HFModuleException e) {
					e.printStackTrace();
				}
				try {
					ModuleInfo mi = ManagerFactory.getManager()
							.getHFModuleHelper()
							.getRemoteModuleInfoByMac("ACCFACCF3389");
					ManagerFactory.getManager().setModule(mi);
				} catch (HFModuleException e) {
					e.printStackTrace();
				}
				try {
					ManagerFactory.getManager().deleteModule("ACCFACCF3389");
				} catch (HFModuleException e) {
					e.printStackTrace();
				}

				try {
					ManagerFactory.getManager().getHFModuleLocalManager()
							.getHFSMTLKHelper().startSmartlinkV30("abcdefg");
					ByteTool.sleep(10);
					ManagerFactory.getManager().getHFModuleLocalManager()
					.getHFSMTLKHelper().stopSmartlinkV30();
				} catch (HFModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ManagerFactory.getManager().getHFModuleLocalManager().getHFSMTLKHelper().startSmartlinkV40("asdf", "asdfasdf");
				ByteTool.sleep(10);
				ManagerFactory.getManager().getHFModuleLocalManager().getHFSMTLKHelper().stopSmartlinkV40();
			
				
			
			*/
				
		/*	while(true){
				remote = ManagerFactory.getManager().getHFModuleHelper()
						.getAllRemoteModuleInfo();
				mylocal = ManagerFactory.getManager().getHFModuleHelper()
						.getMyLocalModuleInfoAll();
				local = ManagerFactory.getManager().getHFModuleHelper()
						.getAllLocalModuleInfo();
				New = ManagerFactory.getManager().getHFModuleHelper()
						.getAllNewModuleInfo();
				Iterator<ModuleInfo > it = remote.iterator();
				while(it.hasNext()){
					ModuleInfo mi = it.next();
					Log.e("remote", mi.getMac());
				}
				
				Iterator<ModuleInfo > it2 = mylocal.iterator();
				while(it2.hasNext()){
					ModuleInfo mi = it2.next();
					Log.e("mylocal", mi.getMac());
				}
				
				Iterator<ModuleInfo > it3 = local.iterator();
				while(it3.hasNext()){
					ModuleInfo mi = it3.next();
					Log.e("local", mi.getMac());
				}
				
				Iterator<ModuleInfo > it4 = New.iterator();
				while(it4.hasNext()){
					ModuleInfo mi = it4.next();
					Log.e("New", mi.getMac());
				}
				ByteTool.sleep(HFConfigration.pulseInterval);
			}*/
			}
		}).start();
	}

	BaseAdapter muadpt = new BaseAdapter() {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mis.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(ModuleList.this).inflate(
					R.layout.listitem, null);
			TextView tv = (TextView) convertView.findViewById(R.id.module);
			if (position < remote.size()) {
				tv.setText("" + mis.get(position).getMac() + ":remote");
			} else if (position < mylocal.size() + remote.size()) {
				tv.setText("" + mis.get(position).getMac() + ":mylocal");
			} else if (position < mylocal.size() + remote.size() + local.size()) {
				tv.setText("" + mis.get(position).getMac() + ":local");
			} else {
				tv.setText("" + mis.get(position).getMac() + ":new");
			}
			return convertView;
		}
	};
}

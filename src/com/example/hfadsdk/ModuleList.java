package com.example.hfadsdk;

import java.util.ArrayList;

import com.hf.ManagerFactory;
import com.hf.info.ModuleInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ModuleList extends Activity{
	private ListView modulelist ;
<<<<<<< HEAD
	ArrayList<ModuleInfo> mis = new ArrayList<>();
=======
	ArrayList<ModuleInfo> mis = new ArrayList<ModuleInfo>();
>>>>>>> 5346b0272205303de565c084b92396d3c166acc3
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
		
		remote = ManagerFactory.getManager().getHFModuleHelper().getAllRemoteModuleInfo();
		mylocal = ManagerFactory.getManager().getHFModuleHelper().getMyLocalModuleInfoAll();
		local = ManagerFactory.getManager().getHFModuleHelper().getAllLocalModuleInfo();
		New = ManagerFactory.getManager().getHFModuleHelper().getAllNewModuleInfo();
		mis.addAll(remote);
		mis.addAll(mylocal);
		mis.addAll(local);
		mis.addAll(New);
		
		modulelist.setAdapter(muadpt);
		muadpt.notifyDataSetChanged();
	}
	
	BaseAdapter muadpt =  new  BaseAdapter(){

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
			convertView = LayoutInflater.from(ModuleList.this).inflate(R.layout.listitem, null);
			TextView tv = (TextView) convertView.findViewById(R.id.module);
			if(position<remote.size()){
				tv.setText(""+mis.get(position).getMac()+":remote");
			}else if(position < mylocal.size()+remote.size()){
				tv.setText(""+mis.get(position).getMac()+":mylocal");
			}else if(position <mylocal.size()+remote.size()+local.size()){
				tv.setText(""+mis.get(position).getMac()+":local");
			}else {
				tv.setText(""+mis.get(position).getMac()+":new");
			}
			return convertView;
		}	
	};
}

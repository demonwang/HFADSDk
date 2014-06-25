package com.hf.helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.hf.data.HFConfigration;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFLocalModuleInfoHelper;

public class HFLocalModuleInfoHelper extends Hashtable<String, ModuleInfo> implements IHFLocalModuleInfoHelper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SharedPreferences sp ;
	
	public HFLocalModuleInfoHelper(){
		sp = HFConfigration.appContex.getSharedPreferences(HFMainUserDataHelper.LOCALDATA, Context.MODE_PRIVATE);
		
		Map<String,String > infos = (Map<String, String>) sp.getAll();
		Iterator<String> it = infos.values().iterator();
		while(it.hasNext()){
			try {
				String data = it.next();
				if(data == null){
					continue;
				}
				JSONObject json = new JSONObject(data);
				ModuleInfo mi = new ModuleInfo();
				mi.fromJson(json);
				if(mi !=null)
					this.put(mi.getMac(), mi);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public synchronized ModuleInfo get(Object key) {
		// TODO Auto-generated method stub
		return super.get(key);
	}

	@Override
	public synchronized ModuleInfo put(String key, ModuleInfo value) {
		// TODO Auto-generated method stub
		ModuleInfo old =  super.put(key, value);
		sp.edit().putString(key, value.toJson()).commit();
		return old;
	}

	@Override
	public synchronized void putAll(
			Map<? extends String, ? extends ModuleInfo> map) {
		// TODO Auto-generated method stub
		super.putAll(map);
		save();
	}

	@Override
	public synchronized ModuleInfo remove(Object key) {
		// TODO Auto-generated method stub
		ModuleInfo old = super.remove(key);
		save();
		return old;
	}

	@Override
	public synchronized void clear() {
		// TODO Auto-generated method stub
		super.clear();
		save();
	}
	
	public ArrayList<ModuleInfo> getAll(){
		ArrayList<ModuleInfo> mis = new ArrayList<ModuleInfo>();
		Iterator<String > it = this.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			mis.add(this.get(key));
		}
		return mis;
	}
	private void save(){
		Editor e = sp.edit();
		e.clear();
		Iterator<ModuleInfo > it = this.values().iterator();
		while(it.hasNext()){
			ModuleInfo mi = it.next();
			e.putString(mi.getMac()	, mi.toJson());
		}
		e.commit();
	}
	@Override
	public void putAll(ArrayList<ModuleInfo> mis) {
		// TODO Auto-generated method stub
		if(mis == null){
			return ;
		}
		this.clear();
		Iterator<ModuleInfo> it = mis.iterator();
		while(it.hasNext()){
			ModuleInfo mi = it.next();
			this.put(mi.getMac(),mi);
		}
		this.save();
	}
	@Override
	public void checkArginTime() {
		// TODO Auto-generated method stub
		Iterator<ModuleInfo> iter = this.values().iterator();
		while (iter.hasNext()) {
	        ModuleInfo mi = (ModuleInfo) iter.next();
	        if(mi.getLocalIp() != null){
		        long lastTimestamp = mi.getLastTimestamp();
		        long nowTimestamp = new java.util.Date().getTime();
		        
		        long tmpTimestap = HFConfigration.pulseInterval*2;
		        
		        if( nowTimestamp > (lastTimestamp + tmpTimestap) ){
		        	mi.setLocalIp(null);
		        }
	        }
        }
		this.save();
	}
}

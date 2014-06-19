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

import com.hf.data.HFConfigration;
import com.hf.info.ModuleInfo;
import com.hf.itf.IHFServerModuleInfoHelper;

public class HFServerModuleInfoHelper extends Hashtable<String, ModuleInfo> implements IHFServerModuleInfoHelper{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SharedPreferences sp ;
	
	public HFServerModuleInfoHelper(){
		sp = HFConfigration.appContex.getSharedPreferences(HFMainUserDataHelper.LOCALDATA, Context.MODE_PRIVATE);
		String data = sp.getString(HFMainUserDataHelper.SERVDATA, "[]");
		try {
			JSONArray jsons = new JSONArray(data);
			for (int i = 0; i < jsons.length(); i++) {
				ModuleInfo mi = new ModuleInfo();
				mi.fromJson(jsons.getJSONObject(i));
				this.put(mi.getMac(), mi);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		save();
		return old;
	}

	@Override
	public synchronized void putAll(
			Map<? extends String, ? extends ModuleInfo> map) {
		// TODO Auto-generated method stub
		this.clear();
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
		Iterator<String > it = this.keySet().iterator();
		JSONArray json = new JSONArray();
		while(it.hasNext()){
			String key = it.next();
			json.put(this.get(key));
		}
		Editor e = sp.edit();
		e.putString(HFMainUserDataHelper.SERVDATA, json.toString());
	}

	@Override
	public void putAll(ArrayList<ModuleInfo> mis) {
		// TODO Auto-generated method stub
		this.clear();
		Iterator<ModuleInfo> it = mis.iterator();
		while(it.hasNext()){
			ModuleInfo mi = it.next();
			this.put(mi.getMac(),mi);
		}
		save();
	}
}

package com.hf.helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaRouter.RouteInfo;

import com.hf.data.HFConfigration;
import com.hf.info.HFLocalRouterInfo;
import com.hf.itf.IHFRouterInfoHelper;

public class HFRouterInfoHelper extends Hashtable<String, String> implements IHFRouterInfoHelper{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SharedPreferences sp ;
	
	public HFRouterInfoHelper(){
		super();
		sp = HFConfigration.appContex.getSharedPreferences(HFLocalSaveHelper.APP_LOCAL_DATA_SP, Context.MODE_PRIVATE);
		try {
			JSONObject json = new JSONObject(sp.getString(HFLocalSaveHelper.ROUTERINFO, "{}"));
			Iterator<String> it = json.keys();
			while(it.hasNext()){
				String key = it.next();
				this.put(key, json.getString(key));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized String put(String key, String value) {
		// TODO Auto-generated method stub
		String oldvalue =  super.put(key, value);
		this.save();
		return oldvalue;
	}
	
	
	
	@Override
	public synchronized void clear() {
		// TODO Auto-generated method stub
		super.clear();
		this.save();
	}

	@Override
	public synchronized void putAll(Map<? extends String, ? extends String> map) {
		// TODO Auto-generated method stub
		super.putAll(map);
		this.save();
	}

	@Override
	public synchronized String remove(Object key) {
		// TODO Auto-generated method stub
		String oldValue =  super.remove(key);
		this.save();
		return oldValue;
	}
	
	private void save(){
		Iterator<String> it = this.keySet().iterator();
		JSONObject json = new JSONObject();
		while(it.hasNext()){
			String key = it.next();
			try {
				json.put(key, this.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Editor e = sp.edit();
		e.putString(HFLocalSaveHelper.ROUTERINFO, json.toString());
		e.commit();
	}

	@Override
	public synchronized String get(Object key) {
		// TODO Auto-generated method stub
		return super.get(key);
	}

	@Override
	public ArrayList<HFLocalRouterInfo> getAll() {
		// TODO Auto-generated method stub
		ArrayList<HFLocalRouterInfo> routers = new ArrayList<HFLocalRouterInfo>();
		Iterator<String> it = this.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			
			routers.add(new HFLocalRouterInfo(key, this.get(key)));
		}
		return routers;
	}
}

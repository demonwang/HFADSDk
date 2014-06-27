package com.hf.itf;

import java.util.ArrayList;
import java.util.Map;

import com.hf.info.HFLocalRouterInfo;


public interface IHFRouterInfoHelper {
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String put(String key, String value);
	/**
	 * 
	 */
	public void clear();
	/**
	 * 
	 * @param map
	 */
	public void putAll(Map<? extends String, ? extends String> map);
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String remove(Object key);
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String get(Object key);
	/**
	 * 
	 * @return
	 */
	public ArrayList<HFLocalRouterInfo> getAll();
}

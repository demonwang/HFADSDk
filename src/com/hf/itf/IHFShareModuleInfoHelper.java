package com.hf.itf;

import java.util.ArrayList;
import java.util.Map;

import com.hf.info.ModuleInfo;

public interface IHFShareModuleInfoHelper {
	/**
	 * 
	 * @param key
	 * @return
	 */
	public ModuleInfo get(Object key);
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ModuleInfo put(String key, ModuleInfo value);
	/**
	 * 
	 * @param map
	 */
	public void putAll(Map<? extends String, ? extends ModuleInfo> map);
	/**
	 * 
	 * @param mis
	 */
	public void putAll(ArrayList<ModuleInfo> mis);
	/**
	 * 
	 * @param key
	 * @return
	 */
	public ModuleInfo remove(Object key);
	/**
	 * 
	 */
	public void clear();
	/**
	 * 
	 * @return
	 */
	public ArrayList<ModuleInfo> getAll();
	/**
	 * 
	 */
	public void checkArginTime();
}

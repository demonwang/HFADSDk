package com.hf.itf;

import java.util.ArrayList;
import java.util.Map;

import com.hf.info.HFLocalRouterInfo;


public interface IHFRouterInfoHelper {
	public String put(String key, String value);
	public void clear();
	public void putAll(Map<? extends String, ? extends String> map);
	public String remove(Object key);
	public String get(Object key);
	public ArrayList<HFLocalRouterInfo> getAll();
}

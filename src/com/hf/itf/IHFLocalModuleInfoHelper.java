package com.hf.itf;

import java.util.ArrayList;
import java.util.Map;

import com.hf.info.ModuleInfo;

public interface IHFLocalModuleInfoHelper {
	public ModuleInfo get(Object key);
	public ModuleInfo put(String key, ModuleInfo value);
	public void putAll(Map<? extends String, ? extends ModuleInfo> map);
	public void putAll(ArrayList<ModuleInfo> mis);
	public ModuleInfo remove(Object key);
	public void clear();
	public ArrayList<ModuleInfo> getAll();
	public void checkArginTime();
}

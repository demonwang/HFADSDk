package com.hf.itf;

public interface IHFMainUserDataHelper {
	public String getUserName();
	public void setUserName(String userName);
	public String getUserNickName();
	public void setUserNickName(String userNickName);
	public String getPswd();
	public void setPswd(String pswd) ;
	public String getPhone();
	public void setPhone(String phone);
	public String getEmail();
	public void setEmail(String email);
	public IHFKeyValueHelper getKeyvalueHelper();
	public IHFLocalModuleInfoHelper getLocalModuleInfoHelper();
	public IHFServerModuleInfoHelper getServerModuleInfoHelper();
	public IHFShareModuleInfoHelper getHFShareModuleInfoHelper();
}

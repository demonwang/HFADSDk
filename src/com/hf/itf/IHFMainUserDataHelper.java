package com.hf.itf;
/**
 * MainUserInfo content class ,if you change you UserInfo please Sync this class
 * @author Administrator
 *
 */
public interface IHFMainUserDataHelper {
	/**
	 * 
	 * @return
	 */
	public String getUserName();
	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName);
	/**
	 * 
	 * @return
	 */
	public String getUserNickName();
	/**
	 * 
	 * @param userNickName
	 */
	public void setUserNickName(String userNickName);
	/**
	 * 
	 * @return
	 */
	public String getPswd();
	/**
	 * 
	 * @param pswd
	 */
	public void setPswd(String pswd) ;
	/**
	 * 
	 * @return
	 */
	public String getPhone();
	/**
	 * 
	 * @param phone
	 */
	public void setPhone(String phone);
	/**
	 * 
	 * @return
	 */
	public String getEmail();
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email);
	/**
	 * 
	 * @return
	 */
	public IHFKeyValueHelper getKeyvalueHelper();
	/**
	 * Get Device Helper of Local
	 * @return
	 */
	public IHFLocalModuleInfoHelper getLocalModuleInfoHelper();
	/**
	 * get Device Helper of server
	 * @return
	 */
	public IHFServerModuleInfoHelper getServerModuleInfoHelper();
	/**
	 *  get  Device Helper int LAN
	 * @return
	 */
	public IHFShareModuleInfoHelper getHFShareModuleInfoHelper();
}

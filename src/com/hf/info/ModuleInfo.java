package com.hf.info;

import org.json.JSONObject;

import com.hf.lib.util.AES;

public class ModuleInfo implements java.io.Serializable {
	
	public static final int TYPE_HF_SMART_LIGHT_WIFI_CT = 0x201;
	public static final int TYPE_HF_SMART_LIGHT_WIFI_RGB = 0x202;
	public static final int TYPE_HF_SMART_LIGHT_WIFI_RGBW = 0x205;
	public static final int TYPE_HF_SMART_LIGHT_WIFI_RGBW_CT = 0x206;
	public static final int TYPE_HF_SMART_LIGHT_GATEWAY_WIFI = 0x301;
	public static final int TYPE_HF_SMART_LIGHT_GATEWAY_ETHERNET = 0x302;
	
	private static final long serialVersionUID = 1L;
	
	private String moduleId;
	/**
	 * HEX string
	 */
	private String mac;
	/**
	 * Coded by base64
	 */
	private String localKey = AES.DEFAULT_KEY_128;
	private Boolean needRemoteControl = true;
	private String serialNo;
	private Integer factoryId = null;
	private Integer type = null;
	private String hardwareVer;
	private String softwareVer;
	/**
	 * Coded by base64
	 */
	private String tempKey;
	/**
	 * yyyyMMddhhmmss
	 */
	private String bindTime;
	private Long totalOnlineTime = null;
	private String internetIp;
	private Double gpsLat = null;
	private Double gpsLng = null;
	/**
	 * Like CN，US，EN
	 */
	private String country;
	private String state;
	private String city;
	private String district;
	private String image;
	private Boolean online = false;
	private Integer nodeType = 1;
	private Integer pid = null;
	private Integer orderNo = null;

	private String localIp;
	private long lastTimestamp = 0;

	// cloud
	private String accessKey;
	private String name;
	private String desc;

	public ModuleInfo() {
		super();
	}
	
	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the localKey
	 */
	public String getLocalKey() {
		return localKey;
	}

	/**
	 * @param localKey the localKey to set
	 */
	public void setLocalKey(String localKey) {
		this.localKey = localKey;
	}

	/**
	 * @return the needRemoteControl
	 */
	public Boolean getNeedRemoteControl() {
		return needRemoteControl;
	}

	/**
	 * @param needRemoteControl the needRemoteControl to set
	 */
	public void setNeedRemoteControl(Boolean needRemoteControl) {
		this.needRemoteControl = needRemoteControl;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the factoryId
	 */
	public Integer getFactoryId() {
		return factoryId;
	}

	/**
	 * @param factoryId the factoryId to set
	 */
	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the hardwareVer
	 */
	public String getHardwareVer() {
		return hardwareVer;
	}

	/**
	 * @param hardwareVer the hardwareVer to set
	 */
	public void setHardwareVer(String hardwareVer) {
		this.hardwareVer = hardwareVer;
	}

	/**
	 * @return the softwareVer
	 */
	public String getSoftwareVer() {
		return softwareVer;
	}

	/**
	 * @param softwareVer the softwareVer to set
	 */
	public void setSoftwareVer(String softwareVer) {
		this.softwareVer = softwareVer;
	}

	/**
	 * @return the tempKey
	 */
	public String getTempKey() {
		return tempKey;
	}

	/**
	 * @param tempKey the tempKey to set
	 */
	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
	}

	/**
	 * @return the bindTime
	 */
	public String getBindTime() {
		return bindTime;
	}

	/**
	 * @param bindTime the bindTime to set
	 */
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	/**
	 * @return the totalOnlineTime
	 */
	public Long getTotalOnlineTime() {
		return totalOnlineTime;
	}

	/**
	 * @param totalOnlineTime the totalOnlineTime to set
	 */
	public void setTotalOnlineTime(Long totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	/**
	 * @return the internetIp
	 */
	public String getInternetIp() {
		return internetIp;
	}

	/**
	 * @param internetIp the internetIp to set
	 */
	public void setInternetIp(String internetIp) {
		this.internetIp = internetIp;
	}

	/**
	 * @return the gpsLat
	 */
	public Double getGpsLat() {
		return gpsLat;
	}

	/**
	 * @param gpsLat the gpsLat to set
	 */
	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}

	/**
	 * @return the gpsLng
	 */
	public Double getGpsLng() {
		return gpsLng;
	}

	/**
	 * @param gpsLng the gpsLng to set
	 */
	public void setGpsLng(Double gpsLng) {
		this.gpsLng = gpsLng;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the online
	 */
	public Boolean getOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(Boolean online) {
		this.online = online;
	}

	/**
	 * @return the nodeType
	 */
	public Integer getNodeType() {
		return nodeType;
	}

	/**
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * @return the orderNo
	 */
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the localIp
	 */
	public String getLocalIp() {
		return localIp;
	}

	/**
	 * @param localIp the localIp to set
	 */
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	/**
	 * @return the lastTimestamp
	 */
	public long getLastTimestamp() {
		return lastTimestamp;
	}

	/**
	 * @param lastTimestamp the lastTimestamp to set
	 */
	public void setLastTimestamp(long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModuleInfo [moduleId=" + moduleId + ", mac=" + mac
				+ ", localKey=" + localKey + ", needRemoteControl="
				+ needRemoteControl + ", serialNo=" + serialNo + ", factoryId="
				+ factoryId + ", type=" + type + ", hardwareVer=" + hardwareVer
				+ ", softwareVer=" + softwareVer + ", tempKey=" + tempKey
				+ ", bindTime=" + bindTime + ", totalOnlineTime="
				+ totalOnlineTime + ", internetIp=" + internetIp + ", gpsLat="
				+ gpsLat + ", gpsLng=" + gpsLng + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", district="
				+ district + ", image=" + image + ", online=" + online
				+ ", nodeType=" + nodeType + ", pid=" + pid + ", orderNo="
				+ orderNo + ", localIp=" + localIp + ", lastTimestamp="
				+ lastTimestamp + ", accessKey=" + accessKey + ", name=" + name
				+ ", desc=" + desc + "]";
	}

	public void fromJson(JSONObject pl) {
		try{this.moduleId = pl.getString("moduleId");}catch(Exception e){}
		try{this.name = pl.getString("name");}catch(Exception e){}
		try{this.desc = pl.getString("desc");}catch(Exception e){}
		try{this.mac = pl.getString("mac");}catch(Exception e){}
		try{this.localKey = pl.getString("localKey");}catch(Exception e){}
		try{this.needRemoteControl = pl.getBoolean("needRemoteControl");}catch(Exception e){}
		try{this.serialNo = pl.getString("serialNo");}catch(Exception e){}
		try{this.factoryId = pl.getInt("factoryId");}catch(Exception e){}
		try{this.type = pl.getInt("type");}catch(Exception e){}
		try{this.hardwareVer = pl.getString("hardwareVer");}catch(Exception e){}
		try{this.softwareVer = pl.getString("softwareVer");}catch(Exception e){}
		try{this.tempKey = pl.getString("tempKey");}catch(Exception e){}
		try{this.bindTime = pl.getString("bindTime");}catch(Exception e){}
		try{this.totalOnlineTime = pl.getLong("totalOnlineTime");}catch(Exception e){}
		try{this.internetIp = pl.getString("internetIp");}catch(Exception e){}
		try{this.gpsLat = pl.getDouble("gpsLat");}catch(Exception e){}
		try{this.gpsLng = pl.getDouble("gpsLng");}catch(Exception e){}
		try{this.country = pl.getString("country");}catch(Exception e){}
		try{this.state = pl.getString("state");}catch(Exception e){}
		try{this.city = pl.getString("city");}catch(Exception e){}
		try{this.district = pl.getString("district");}catch(Exception e){}
		try{this.online = pl.getBoolean("online");}catch(Exception e){}
		try{this.localIp = pl.getString("localIp");}catch(Exception e){}
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		if(moduleId != null){
			try{json.put ("moduleId", this.moduleId);}catch(Exception e){}
		}
		if(accessKey != null){
			try{json.put ("accessKey", this.accessKey);}catch(Exception e){}
		}
		if(name != null){
			try{json.put ("name", this.name);}catch(Exception e){}
		}
		if(desc != null){
			try{json.put ("desc", this.desc);}catch(Exception e){}
		}
		if(mac != null){
			try{json.put ("mac", this.mac);}catch(Exception e){}
		}
		if(localKey != null){
			try{json.put ("localKey", this.localKey);}catch(Exception e){}
		}
		if(needRemoteControl != null){
			try{json.put ("needRemoteControl", this.needRemoteControl);}catch(Exception e){}
		}
		if(serialNo != null){
			try{json.put ("serialNo", this.serialNo);}catch(Exception e){}
		}
		if(factoryId != null){
			try{json.put ("factoryId", this.factoryId);}catch(Exception e){}
		}
		if(type != null){
			try{json.put ("type", this.type);}catch(Exception e){}
		}
		if(hardwareVer != null){
			try{json.put ("hardwareVer", this.hardwareVer);}catch(Exception e){}
		}
		if(softwareVer != null){
			try{json.put ("softwareVer", this.softwareVer);}catch(Exception e){}
		}
		if(tempKey != null){
			try{json.put ("tempKey", this.tempKey);}catch(Exception e){}
		}
		if(bindTime != null){
			try{json.put ("bindTime", this.bindTime);}catch(Exception e){}
		}
		if(totalOnlineTime != null){
			try{json.put ("totalOnlineTime", this.totalOnlineTime);}catch(Exception e){}
		}
		if(internetIp != null){
			try{json.put ("internetIp", this.internetIp);}catch(Exception e){}
		}
		if(gpsLat != null){
			try{json.put ("gpsLat", this.gpsLat);}catch(Exception e){}
		}
		if(gpsLng != null){
			try{json.put ("gpsLng", this.gpsLng);}catch(Exception e){}
		}
		if(country != null){
			try{json.put ("country", this.country);}catch(Exception e){}
		}
		if(state != null){
			try{json.put ("state", this.state);}catch(Exception e){}
		}
		if(city != null){
			try{json.put ("city", this.city);}catch(Exception e){}
		}
		if(district != null){
			try{json.put ("district", this.district);}catch(Exception e){}
		}
		if(online != null){
			try{json.put ("online", this.online);}catch(Exception e){}
		}
		if(localIp != null){
			try{json.put("localIp", this.localIp);}catch(Exception e){}
		}
		if(lastTimestamp != 0){
			try{json.put("lastTimestamp", this.lastTimestamp);}catch(Exception e){}
		}
		return json.toString();
	}
}

package com.hf.info;

import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;

import com.hf.lib.util.AES;

public class ModuleInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// local
	private String localIp = null;
	private long lastTimestamp = 0;

	// cloud
	private String id = null;
	private String accessKey = null;
	private String name = null;
	private String desc = null;

	private String mac = null; // HEX string
	private String localKey = null; // Coded by base64
	private Boolean needRemoteControl = true;

	private String serialNo = null;
	private Integer factoryId = null;
	private Integer type = null;
	private String hardwareVer = null;
	private String softwareVer = null;
	private String tempKey = null; // Coded by base64
	private String bindTime = null;
	private Long totalOnlineTime = null;

	private String internetIp = null;
	private Double gpsLat = null;
	private Double gpsLng = null;
	private String country = null;
	private String state = null;
	private String city = null;
	private String district = null;

	private Boolean online = false;

	public ModuleInfo() {
		super();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ModuleInfo[");
		sb.append(super.toString()).append(",");

		if (localIp != null)
			sb.append("localIp=").append(localIp).append(",");
		if (id != null)
			sb.append("id=").append(id).append(",");
		if (accessKey != null)
			sb.append("accessKey=").append(accessKey).append(",");
		if (name != null)
			sb.append("name=").append(name).append(",");
		if (desc != null)
			sb.append("desc=").append(desc).append(",");
		if (mac != null)
			sb.append("mac=").append(mac).append(",");
		if (localKey != null)
			sb.append("localKey=").append(localKey).append(",");
		if (needRemoteControl != null)
			sb.append("needRemoteControl=").append(needRemoteControl)
					.append(",");
		if (serialNo != null)
			sb.append("serialNo=").append(serialNo).append(",");
		if (factoryId != null)
			sb.append("factoryId=").append(factoryId).append(",");
		if (type != null)
			sb.append("type=").append(type).append(",");
		if (hardwareVer != null)
			sb.append("hardwareVer=").append(hardwareVer).append(",");
		if (softwareVer != null)
			sb.append("softwareVer=").append(softwareVer).append(",");
		if (tempKey != null)
			sb.append("tempKey=").append(tempKey).append(",");
		if (bindTime != null)
			sb.append("bindTime=").append(bindTime).append(",");
		if (totalOnlineTime != null)
			sb.append("totalOnlineTime=").append(totalOnlineTime).append(",");
		if (internetIp != null)
			sb.append("internetIp=").append(internetIp).append(",");
		if (gpsLat != null)
			sb.append("gpsLat=").append(gpsLat).append(",");
		if (gpsLng != null)
			sb.append("gpsLng=").append(gpsLng).append(",");
		if (country != null)
			sb.append("country=").append(country).append(",");
		if (state != null)
			sb.append("state=").append(state).append(",");
		if (city != null)
			sb.append("city=").append(city).append(",");
		if (district != null)
			sb.append("district=").append(district).append(",");
		if (online != null)
			sb.append("online=").append(online);
		sb.append("]");
		return sb.toString();
	}

	public long getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		Log.e("localIp", localIp+"");
		this.localIp = localIp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getLocalKey() {
		if (localKey == null)
			localKey = AES.DEFAULT_KEY_128;
		return localKey;
	}

	public void setLocalKey(String localKey) {
		this.localKey = localKey;
	}

	public Boolean getNeedRemoteControl() {
		return needRemoteControl;
	}

	public void setNeedRemoteControl(Boolean needRemoteControl) {
		this.needRemoteControl = needRemoteControl;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getFactoryId() {
		return this.factoryId;
	}

	public void setFactoryId(Integer factoryId) {
		this.factoryId = factoryId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHardwareVer() {
		return hardwareVer;
	}

	public void setHardwareVer(String hardwareVer) {
		this.hardwareVer = hardwareVer;
	}

	public String getSoftwareVer() {
		return softwareVer;
	}

	public void setSoftwareVer(String softwareVer) {
		this.softwareVer = softwareVer;
	}

	public String getTempKey() {
		return tempKey;
	}

	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
	}

	public String getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}

	public Long getTotalOnlineTime() {
		return totalOnlineTime;
	}

	public void setTotalOnlineTime(Long totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	public String getInternetIp() {
		return internetIp;
	}

	public void setInternetIp(String internetIp) {
		this.internetIp = internetIp;
	}

	public Double getGpsLat() {
		return gpsLat;
	}

	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public Double getGpsLng() {
		return gpsLng;
	}

	public void setGpsLng(Double gpsLng) {
		this.gpsLng = gpsLng;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public void fromJson(JSONObject pl) {
		try{this.id = pl.getString("moduleId");}catch(Exception e){}
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
		if(id != null){
			try{json.put ("moduleId", this.id);}catch(Exception e){}
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

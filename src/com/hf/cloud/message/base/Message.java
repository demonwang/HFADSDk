package com.hf.cloud.message.base;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
	
	private int serialNumber;
	private int classId = 0;
	private String sessionId;
	private String name = "Message";
	
	/**
	 * @return the serialNumber
	 */
	@JSONField(name="SN")
	public int getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * @param serialNumber the serialNumber to set
	 */
	@JSONField(name="SN")
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	/**
	 * @return the classId
	 */
	@JSONField(name="CID")
	public int getClassId() {
		return classId;
	}
	
	/**
	 * @param classId the classId to set
	 */
	@JSONField(name="CID")
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	/**
	 * @return the sessionId
	 */
	@JSONField(name="SID")
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * @param sessionId the sessionId to set
	 */
	@JSONField(name="SID")
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * @return the name
	 */
	@JSONField(name="NM")
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	@JSONField(name="NM")
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [serialNumber=" + serialNumber + ", classId=" + classId
				+ ", sessionId=" + sessionId + ", name=" + name + "]";
	}
}

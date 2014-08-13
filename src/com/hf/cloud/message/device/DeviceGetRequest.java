package com.hf.cloud.message.device;

public class DeviceGetRequest extends DeviceRequest {

	public DeviceGetRequest() {
		super();
		setClassId(20021);
	}

	@Override
	public String toString() {
		return "DeviceSetRequest [toString()=" + super.toString() + "]";
	}
}

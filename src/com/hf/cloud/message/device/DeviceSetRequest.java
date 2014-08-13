package com.hf.cloud.message.device;

public class DeviceSetRequest extends DeviceRequest {

	public DeviceSetRequest() {
		super();
		setClassId(20031);
	}

	@Override
	public String toString() {
		return "DeviceSetRequest [toString()=" + super.toString() + "]";
	}
}

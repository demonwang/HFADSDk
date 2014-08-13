package com.hf.cloud.manager;

import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.device.DeviceEventRequest;
import com.hf.cloud.message.device.DeviceEventResponse;
import com.hf.cloud.message.device.DeviceGetRequest;
import com.hf.cloud.message.device.DeviceResponse;
import com.hf.cloud.message.device.DeviceSetRequest;

public interface ICloudDeviceManager {

	public DeviceEventResponse getEvent(DeviceEventRequest request) throws CloudException;
	public DeviceResponse getDevice(DeviceGetRequest request) throws CloudException;
	public DeviceResponse setDevice(DeviceSetRequest request) throws CloudException;
}

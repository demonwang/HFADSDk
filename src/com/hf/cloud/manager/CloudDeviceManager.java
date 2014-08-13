package com.hf.cloud.manager;

import com.hf.cloud.config.CloudConfig;
import com.hf.cloud.exception.CloudException;
import com.hf.cloud.message.device.DeviceEventRequest;
import com.hf.cloud.message.device.DeviceEventResponse;
import com.hf.cloud.message.device.DeviceGetRequest;
import com.hf.cloud.message.device.DeviceResponse;
import com.hf.cloud.message.device.DeviceSetRequest;

public class CloudDeviceManager extends BaseCloudManager implements
		ICloudDeviceManager {

	public CloudDeviceManager(CloudConfig cloudConfig) {
		super(cloudConfig);
	}

	@Override
	public DeviceEventResponse getEvent(DeviceEventRequest request)
			throws CloudException {
		return postRequest(request, DeviceEventResponse.class);
	}

	@Override
	public DeviceResponse getDevice(DeviceGetRequest request)
			throws CloudException {
		return postRequest(request, DeviceResponse.class);
	}

	@Override
	public DeviceResponse setDevice(DeviceSetRequest request)
			throws CloudException {
		return postRequest(request, DeviceResponse.class);
	}
}

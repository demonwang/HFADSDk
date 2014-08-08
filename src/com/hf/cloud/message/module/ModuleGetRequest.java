package com.hf.cloud.message.module;

import com.hf.cloud.message.module.payload.ModuleIdPayload;

public class ModuleGetRequest extends ModuleIdRequest {

	public ModuleGetRequest() {
		this(null);
	}

	public ModuleGetRequest(ModuleIdPayload payload) {
		super(payload);
		setClassId(30031);
	}
}

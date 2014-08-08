package com.hf.cloud.message.module;

import com.hf.cloud.message.module.payload.ModuleIdPayload;

public class ModuleDeleteRequest extends ModuleIdRequest {

	public ModuleDeleteRequest() {
		this(null);
	}

	public ModuleDeleteRequest(ModuleIdPayload payload) {
		super(payload);
		setClassId(30021);
	}
}

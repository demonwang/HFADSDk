package com.hf.cloud.message.base;

public class Alarm extends Message {

	public Alarm() {
		super();
		setClassId(8);
		setName("Alarm");
	}
}

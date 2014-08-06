package com.hf.cloud.message.base;

public class Event extends Message {

	public Event() {
		super();
		setClassId(4);
		setName("Event");
	}
}

package com.interpretor.types;

public class Value_Boolean extends Data {
	public Value_Boolean(boolean value) throws Exception {
		super(new Value(value, Boolean.class).getInstance(Boolean.TYPE));
	}
}

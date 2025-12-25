package com.interpretor.types;

public class Value_Float extends Data{
	public Value_Float(float value) throws Exception {
		super(new Value(value, Float.class).getInstance(Float.TYPE));
	}
}

package com.interpretor.types;

public class Value_Long extends Number{
	public Value_Long(long value) throws Exception {
		super(new Value(value, Long.class).getInstance(Long.TYPE));
	}
}

package com.interpretor.types;

public class Value_Long extends Data{
	public Value_Long(long value) throws Exception {
		super(new Value(value, Long.class).getInstance(Long.TYPE));
	}
	@Override
	public String toString() {
		return String.valueOf(this.Data);
	}
}
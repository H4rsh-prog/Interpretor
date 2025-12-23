package com.interpretor.types;

public class Value_Integer extends Number{
	public Value_Integer(int value) throws Exception {
		super(new Value(value, Integer.class).getInstance(Integer.TYPE));
	}
}

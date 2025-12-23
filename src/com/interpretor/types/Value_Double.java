package com.interpretor.types;

public class Value_Double extends Number{
	public Value_Double(double value) throws Exception {
		super(new Value(value, Double.class).getInstance(Double.TYPE));
	}
}

package com.interpretor.types;

public class Value_Double extends Data{
	public Value_Double(double value) throws Exception {
		super(new Value(value, Double.class).getInstance(Double.TYPE));
	}
	@Override
	public String toString() {
		return String.valueOf(this.Data);
	}
}

package com.interpretor.types;

public class Value_Boolean extends Data {
	public Value_Boolean(boolean value) throws Exception {
		super(new Value(value, Boolean.class).getInstance(Boolean.TYPE));
	}

	@Override
	public Object add(com.interpretor.types.Data DATA) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object sub(com.interpretor.types.Data DATA) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object mul(com.interpretor.types.Data DATA) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object div(com.interpretor.types.Data DATA) {
		// TODO Auto-generated method stub
		return null;
	}
}

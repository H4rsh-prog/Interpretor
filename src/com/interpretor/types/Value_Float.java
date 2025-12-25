package com.interpretor.types;

public class Value_Float extends Data{
	public Value_Float(float value) throws Exception {
		super(new Value(value, Float.class).getInstance(Float.TYPE));
	}
	

	public float add(int DATA) {
		return ((float)this.Data) + DATA;
	}
	public float add(long DATA) {
		return ((float)this.Data) + DATA;
	}
	public double add(double DATA) {
		return ((float)this.Data) + DATA;
	}
	public float add(float DATA) {
		return ((float)this.Data) + DATA;
	}
	public String add(String DATA) {
		return ((float)this.Data)+DATA;
	}
	
	public float sub(int DATA) {
		return ((float)this.Data) - DATA;
	}
	public float sub(long DATA) {
		return ((float)this.Data) - DATA;
	}
	public double sub(double DATA) {
		return ((float)this.Data) - DATA;
	}
	public float sub(float DATA) {
		return ((float)this.Data) - DATA;
	}

	public float mul(int DATA) {
		return ((float)this.Data) - DATA;
	}
	public float mul(long DATA) {
		return ((float)this.Data) - DATA;
	}
	public double mul(double DATA) {
		return ((float)this.Data) - DATA;
	}
	public float mul(float DATA) {
		return ((float)this.Data) - DATA;
	}
	
	public float div(int DATA) {
		return ((float)this.Data) - DATA;
	}
	public float div(long DATA) {
		return ((float)this.Data) - DATA;
	}
	public double div(double DATA) {
		return ((float)this.Data) - DATA;
	}
	public float div(float DATA) {
		return ((float)this.Data) - DATA;
	}


	@Override
	public Object add(com.interpretor.types.Data DATA) {
		try {
			return this.add(Value.allocateOriginalDataType(DATA));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Object sub(com.interpretor.types.Data DATA) {
		try {
			return this.sub(Value.allocateOriginalDataType(DATA));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Object mul(com.interpretor.types.Data DATA) {
		try {
			return this.mul(Value.allocateOriginalDataType(DATA));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Object div(com.interpretor.types.Data DATA) {
		try {
			return this.div(Value.allocateOriginalDataType(DATA));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

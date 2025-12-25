package com.interpretor.types;

public class Value_Integer extends Data{
	public Value_Integer(int value) throws Exception {
		super(new Value(value, Integer.class).getInstance(Integer.TYPE));
	}
	
	public int add(int DATA) {
		return ((int)this.Data) + DATA;
	}
	public long add(long DATA) {
		return ((int)this.Data) + DATA;
	}
	public double add(double DATA) {
		return ((int)this.Data) + DATA;
	}
	public float add(float DATA) {
		return ((int)this.Data) + DATA;
	}
	public String add(String DATA) {
		return ((int)this.Data)+DATA;
	}
	
	public int sub(int DATA) {
		return ((int)this.Data) - DATA;
	}
	public long sub(long DATA) {
		return ((int)this.Data) - DATA;
	}
	public double sub(double DATA) {
		return ((int)this.Data) - DATA;
	}
	public float sub(float DATA) {
		return ((int)this.Data) - DATA;
	}

	public int mul(int DATA) {
		return ((int)this.Data) - DATA;
	}
	public long mul(long DATA) {
		return ((int)this.Data) - DATA;
	}
	public double mul(double DATA) {
		return ((int)this.Data) - DATA;
	}
	public float mul(float DATA) {
		return ((int)this.Data) - DATA;
	}
	
	public float div(int DATA) {
		return ((int)this.Data) - DATA;
	}
	public float div(long DATA) {
		return ((int)this.Data) - DATA;
	}
	public double div(double DATA) {
		return ((int)this.Data) - DATA;
	}
	public float div(float DATA) {
		return ((int)this.Data) - DATA;
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

package com.interpretor.types;

public class Value_Long extends Data{
	public Value_Long(long value) throws Exception {
		super(new Value(value, Long.class).getInstance(Long.TYPE));
	}
	

	public long add(int DATA) {
		return ((long)this.Data) + DATA;
	}
	public long add(long DATA) {
		return ((long)this.Data) + DATA;
	}
	public double add(double DATA) {
		return ((long)this.Data) + DATA;
	}
	public float add(float DATA) {
		return ((long)this.Data) + DATA;
	}
	public String add(String DATA) {
		return ((long)this.Data)+DATA;
	}
	
	public long sub(int DATA) {
		return ((long)this.Data) - DATA;
	}
	public long sub(long DATA) {
		return ((long)this.Data) - DATA;
	}
	public double sub(double DATA) {
		return ((long)this.Data) - DATA;
	}
	public float sub(float DATA) {
		return ((long)this.Data) - DATA;
	}

	public long mul(int DATA) {
		return ((long)this.Data) - DATA;
	}
	public long mul(long DATA) {
		return ((long)this.Data) - DATA;
	}
	public double mul(double DATA) {
		return ((long)this.Data) - DATA;
	}
	public float mul(float DATA) {
		return ((long)this.Data) - DATA;
	}
	
	public float div(int DATA) {
		return ((long)this.Data) - DATA;
	}
	public float div(long DATA) {
		return ((long)this.Data) - DATA;
	}
	public double div(double DATA) {
		return ((long)this.Data) - DATA;
	}
	public float div(float DATA) {
		return ((long)this.Data) - DATA;
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

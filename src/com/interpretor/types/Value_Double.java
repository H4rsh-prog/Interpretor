package com.interpretor.types;

public class Value_Double extends Data{
	public Value_Double(double value) throws Exception {
		super(new Value(value, Double.class).getInstance(Double.TYPE));
	}
	

	public double add(int DATA) {
		return ((double)this.Data) + DATA;
	}
	public double add(long DATA) {
		return ((double)this.Data) + DATA;
	}
	public double add(double DATA) {
		return ((double)this.Data) + DATA;
	}
	public double add(float DATA) {
		return ((double)this.Data) + DATA;
	}
	public String add(String DATA) {
		return ((double)this.Data)+DATA;
	}
	
	public double sub(int DATA) {
		return ((double)this.Data) - DATA;
	}
	public double sub(long DATA) {
		return ((double)this.Data) - DATA;
	}
	public double sub(double DATA) {
		return ((double)this.Data) - DATA;
	}
	public double sub(float DATA) {
		return ((double)this.Data) - DATA;
	}

	public double mul(int DATA) {
		return ((double)this.Data) - DATA;
	}
	public double mul(long DATA) {
		return ((double)this.Data) - DATA;
	}
	public double mul(double DATA) {
		return ((double)this.Data) - DATA;
	}
	public double mul(float DATA) {
		return ((double)this.Data) - DATA;
	}
	
	public double div(int DATA) {
		return ((double)this.Data) - DATA;
	}
	public double div(long DATA) {
		return ((double)this.Data) - DATA;
	}
	public double div(double DATA) {
		return ((double)this.Data) - DATA;
	}
	public double div(float DATA) {
		return ((double)this.Data) - DATA;
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

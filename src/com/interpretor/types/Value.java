package com.interpretor.types;

public class Value<T>{
	private Class class_type;
	private Object value = null;
	Value(T value, Class<T> class_type){
		this.class_type = class_type;
		this.value = value;
	}
	T getInstance(Class<T> TYPE) throws Exception{
		System.out.println("TYPE = "+TYPE+" CLASS = "+this.class_type+" VALUE = "+this.value);
		return (T) this.class_type.getConstructor(TYPE).newInstance(this.value);
	}
	public static <T> T allocateDataType(T inp) throws Exception {
		Class type = inp.getClass();
		if(type.isInstance(new Long(0))) {
			long data = (long) inp;
			if(data>=Integer.MIN_VALUE && data<=Integer.MAX_VALUE) {
				return (T) new Value_Integer((int) data);
			} else {
				return (T) new Value_Long(data);
			}
		} else if(type.isInstance(new Integer(0))) {
			int data = (int) inp;
			return (T) new Value_Integer((int) data);
		} else if(type.isInstance(new Double(0))) {
			double data = (double) inp;
			if(data>=Float.MIN_VALUE && data<=Float.MAX_VALUE) {
				return (T) new Value_Float((float) data);
			} else {
				return (T) new Value_Double(data);
			}
		} else if(type.isInstance(new Float(0))) {
			float data = (float) inp;
			return (T) new Value_Float((float) data);
		} else if (type.isInstance(new Boolean(true))) {
			boolean data = (boolean) inp;
			return (T) new Value_Boolean(data);
		} else {
			return (T) String.valueOf(inp);
		}
	}
}

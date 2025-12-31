package com.interpretor.types;

public class Value<T>{
	private Class class_type;
	private Object value = null;
	Value(T value, Class<T> class_type){
		this.class_type = class_type;
		this.value = value;
	}
	T getInstance(Class<T> TYPE) throws Exception{
		System.out.println("NEW VALUE CREATED TYPE = "+TYPE+" CLASS = "+this.class_type+" VALUE = "+this.value);
		return (T) this.class_type.getConstructor(TYPE).newInstance(this.value);
	}
	public static <T, R> R allocateDataType(T inp) throws Exception {
		Class type = inp.getClass();
		if(Number.class.isInstance(inp)) {
			int dt =Data.getDTpriority(type.getTypeName());
			Number data = (Number)inp;
			switch(dt) {
				case 2:
				case 1:
					if(data.intValue()>=Integer.MIN_VALUE && data.intValue()<=Integer.MAX_VALUE) {
						return (R) new Value_Integer(data.intValue());
					}
					return (R) new Value_Long(data.longValue());
				case 4:
				case 3:
					if(data.floatValue()>=Float.MIN_VALUE && data.floatValue()<=Float.MAX_VALUE) {
						return (R) new Value_Float(data.floatValue());
					}
					return (R) new Value_Double(data.doubleValue());
			}
		} else {
			if(Boolean.FALSE == inp || Boolean.TRUE == inp) {
				return (R) new Value_Boolean((boolean) inp);
			}
		}
		return (R) String.valueOf(inp);
	}
	public static <T> T allocateOriginalDataType(Data inp) throws Exception {
		Class originalType = inp.Data.getClass();
		return (T) originalType.getDeclaredConstructor(originalType).newInstance(inp.Data);
	}
}
